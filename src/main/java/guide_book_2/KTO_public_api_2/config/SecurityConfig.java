package guide_book_2.KTO_public_api_2.config;

import guide_book_2.KTO_public_api_2.jwt.JWTFilter;
import guide_book_2.KTO_public_api_2.jwt.JWTUtil;
import guide_book_2.KTO_public_api_2.jwt.LoginFilter;
import guide_book_2.KTO_public_api_2.service.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity //ウェブ セキュリティ設定を有効にする　
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, CustomOAuth2UserService customOAuth2UserService) {

        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
    //BCryptはパスワードの暗号化に使用できるメソッド

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                })));

        //Springのバージョンによって使い方が変わるので注意
        http
                .authorizeHttpRequests((auth)->auth //requestMatchers()は特定なアドレスを意味する
                        .requestMatchers("/**").permitAll()
                        //permitAll()はみんなに接続を許可します
                        //.requestMatchers().authenticated() 로그인을 해야 접근 가능한 곳
                        //authenticated()はログイン後に接続できます
                        //.requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated());
        //.anyRequest()は他に設定できなかった他の経路を設定します
        //.requestMatchers("/home/**")で**はワイルドカードを意味し/home/　後に来るURL全てを意味します
        //hasRole("ADMIN", "USER')など（）中に色んな役割を入れて接触許可を許します

                        /*
                        注意！！！　設定の動作順序は上から下なので注意が必要
                        例えば
                        .anyRequest().permitAll()
                        .requestMatchers("/home/**","bookMark/**","guideBook/**").authenticated()
                        の順序だとすでに.permitAll()をしたので意味ない（適用されない）
                        */

        //From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        http
                .csrf((auth)->auth.disable());

        http
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        /*
                        "/loginProc"がにフォームに入力したusernameやpasswordを含むリクエストが/loginProcに送信され、
                        スプリングセキュリティがその情報を使用してユーザーを認証します
                        */
                        .permitAll());
                /*
                auth.loginPageはスプリングセキュリティは、ユーザーが認証されていない状態で保護されてる
                リソースにアクセスしようとした場合、認証ページにリダイレクトします。

                もし"ADMIN"じゃない利用者が "/admin"ページに入った時誤謬(ごびゅう）ページが出るんじゃなく
                ログインページを出すメソッドです
                */


        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        //필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);


        //세션을 스테이리스 상태
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //oauth2
        http
                .oauth2Login((oatuh2) -> oatuh2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)));

        return http.build();


    }
}