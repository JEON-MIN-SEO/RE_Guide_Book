package guide_book_2.KTO_public_api_2.controller.api;

import guide_book_2.KTO_public_api_2.dto.DayDTO;
import guide_book_2.KTO_public_api_2.dto.GuidebookDTO;
import guide_book_2.KTO_public_api_2.service.GuidebookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guidebook")
public class GuidebookAPI {

    private final GuidebookService guidebookService;

    public GuidebookAPI(GuidebookService guidebookService) {
        this.guidebookService = guidebookService;
    }

    // 가이드북 생성
    @PostMapping("/create")
    public ResponseEntity<String> createGuidebook(@RequestBody GuidebookDTO guidebookDTO) {
        guidebookService.createGuidebook(guidebookDTO);
        return ResponseEntity.ok("Guidebook created successfully");
    }
/*
    // Day 콘텐츠 추가 요청
    @PostMapping("/{guidebookId}/addDay")
    public ResponseEntity<String> addDayToGuidebook(@PathVariable Long guidebookId, @RequestBody DayDTO dayDTO) {
        guidebookService.addDayToGuidebook(guidebookId, dayDTO);
        return ResponseEntity.ok("Day added successfully");
    }
*/
}
