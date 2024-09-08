package guide_book_2.KTO_public_api_2.controller.api;

import guide_book_2.KTO_public_api_2.dto.DayDTO;
import guide_book_2.KTO_public_api_2.dto.GuidebookDTO;
import guide_book_2.KTO_public_api_2.service.GuidebookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guidebook")
public class GuidebookAPI {

    private final GuidebookService guidebookService;

    public GuidebookAPI(GuidebookService guidebookService) {
        this.guidebookService = guidebookService;
    }

    // 사용자가 만든 모든 가이드북 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GuidebookDTO>> getGuidebooksByUserId(@PathVariable("userId") Long userId) {
        List<GuidebookDTO> guidebooks = guidebookService.getGuidebooksByUserId(userId);
        return ResponseEntity.ok(guidebooks);
    }

    // 가이드북 생성
    @PostMapping("/create")
    public ResponseEntity<String> createGuidebook(@RequestBody GuidebookDTO guidebookDTO) {
        guidebookService.createGuidebook(guidebookDTO);
        return ResponseEntity.ok("Guidebook created successfully");
        //에러 코드 포함 필요
    }

    //일정 추가
    @PostMapping("/{guidebookId}/updateDays")
    public ResponseEntity<String> updateDaysInGuidebook(@PathVariable("guidebookId") Long guidebookId, @RequestBody List<DayDTO> dayDTOs) {
        guidebookService.updateDaysInGuidebook(guidebookId, dayDTOs);
        return ResponseEntity.ok("Days updated successfully");
    }



    // 가이드북 삭제
    @DeleteMapping("/{guidebookId}")
    public ResponseEntity<String> deleteGuidebook(@PathVariable Long guidebookId) {
        guidebookService.deleteGuidebook(guidebookId);
        return ResponseEntity.ok("Guidebook deleted successfully");
    }
}
