package guide_book_2.KTO_public_api_2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayDTO {
    private int dayNumber;   // 하루의 번호 (1, 2, 3...)
    private String contentJson;  // 하루의 콘텐츠 (계획)
}
