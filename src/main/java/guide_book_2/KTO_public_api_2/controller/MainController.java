package guide_book_2.KTO_public_api_2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/main")
    public String MainPage() {
        return "main";
    }

    @GetMapping("/login")
    public String LoginPage() {
        return "login";
    }

    @GetMapping("/join")
    public String JoinPage() {
        return "join";
    }
}
