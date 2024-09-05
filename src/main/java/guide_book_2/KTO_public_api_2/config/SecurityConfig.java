package guide_book_2.KTO_public_api_2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //ウェブ セキュリティ設定を有効にする　
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
    //BCryptはパスワードの暗号化に使用できるメソッド

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Springのバージョンによって使い方が変わるので注意
        http
                .authorizeHttpRequests((auth)->auth //requestMatchers()は特定なアドレスを意味する
                        .requestMatchers("/**", "/login", "/join/joinProc","/api/bookmark/**").permitAll()
                        //permitAll()はみんなに接続を許可します
                        .requestMatchers("/home/**","bookMark/**","guideBook/**").authenticated()
                        //authenticated()はログイン後に接続できます
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
        return http.build();
    }
}