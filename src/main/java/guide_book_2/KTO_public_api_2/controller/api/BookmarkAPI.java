package guide_book_2.KTO_public_api_2.controller.api;

import guide_book_2.KTO_public_api_2.dto.BookmarkDTO;
import guide_book_2.KTO_public_api_2.service.BookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//CRUD 「作成（Create）」「読み出し（Read）」「更新（Update）」「削除（Delete）」
@RestController
@RequestMapping("/api/bookmark")
public class BookmarkAPI {

    private final BookmarkService bookmarkService;

    public BookmarkAPI(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBookmark(@RequestBody BookmarkDTO bookmarkDTO) {
        try {
            bookmarkService.addBookmark(bookmarkDTO);
            return ResponseEntity.ok("Bookmark added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
