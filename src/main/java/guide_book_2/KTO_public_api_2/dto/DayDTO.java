package guide_book_2.KTO_public_api_2.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DayDTO {
    private int dayNumber;   // 하루의 번호 (1, 2, 3...)
    private List<String> contentIds;  // 하루의 콘텐츠 ID 리스트
}