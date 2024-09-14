//package guide_book_2.KTO_public_api_2.ouath2;
//
//import guide_book_2.KTO_public_api_2.dto.CustomOAuth2User;
//import guide_book_2.KTO_public_api_2.jwt.JWTUtil;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//    private final JWTUtil jwtUtil;
//
//    public CustomSuccessHandler(JWTUtil jwtUtil){
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        // OAuth2User
//        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
//
//        // LineUserId 가져오기
//        Long lineUserId = customUserDetails.getLineUserId();
//
//        // JWT 생성 (username을 lineUserId로 변경)
//        String token = jwtUtil.createJwt(lineUserId);
//
//        // 쿠키 추가
//        response.addCookie(createCookie("Authorization", token));
//        response.sendRedirect("http://localhost:3000/");
//    }
//
//    private Cookie createCookie(String key, String value) {
//
//        Cookie cookie = new Cookie(key, value);
//        cookie.setMaxAge(60*60*60);
//        //cookie.setSecure(true);
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//
//        return cookie;
//    }
//}
//
