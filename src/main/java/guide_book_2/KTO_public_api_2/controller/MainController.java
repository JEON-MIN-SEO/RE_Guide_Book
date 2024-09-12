package guide_book_2.KTO_public_api_2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // 로그인 페이지로 이동
    @GetMapping("/loginP")
    public String MainPage() {
        return "login";
    }

    // 회원가입 페이지로 이동
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";  // signup.html로 이동
    }
}
