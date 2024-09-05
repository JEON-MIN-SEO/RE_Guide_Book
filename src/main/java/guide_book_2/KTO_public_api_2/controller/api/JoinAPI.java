package guide_book_2.KTO_public_api_2.controller.api;

import guide_book_2.KTO_public_api_2.dto.UserDTO;
import guide_book_2.KTO_public_api_2.error.CustomException;
import guide_book_2.KTO_public_api_2.service.JoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class JoinAPI {

    private final JoinService joinService;

    public JoinAPI (JoinService joinService) {
        this.joinService = joinService;
    }

    //フロントでリダイレクトするコード
    @PostMapping("/joinProc")
    public ResponseEntity<String> JoinProcess(@RequestBody UserDTO userDTO) {
        try {
            joinService.JoinProcess(userDTO);
            return ResponseEntity.status(HttpStatus.OK).body("User registered successfully");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(String.format("{\"error_id\": %d, \"error_message\": \"%s\"}", e.getErrorCode(), e.getMessage()));
        }
        /*
        catch (Exception e) {
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        */
        /*　サーバ側のリダイレクトする方法
    @PostMapping("/joinProc")
    public RedirectView JoinProcess(@RequestBody UserDTO userDTO) {
        try {
            joinService.JoinProcess(userDTO);
            return new RedirectView("/login");
        } catch (Exception e) {
            return new RedirectView("/join?error=true");
        }
    }
    */
    }
}
