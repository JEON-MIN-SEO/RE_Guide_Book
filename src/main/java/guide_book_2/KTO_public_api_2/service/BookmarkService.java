package guide_book_2.KTO_public_api_2.service;

import guide_book_2.KTO_public_api_2.dto.BookmarkDTO;
import guide_book_2.KTO_public_api_2.entity.BookmarkEntity;
import guide_book_2.KTO_public_api_2.entity.UserEntity;
import guide_book_2.KTO_public_api_2.repository.BookmarkRepository;
import guide_book_2.KTO_public_api_2.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service//ブックマークについてのサービス
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository, UserRepository userRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
    }

    //作成
    public void addBookmark(BookmarkDTO bookmarkDTO) {
        // 유효성 검사: 빈 값인지 확인
        if (bookmarkDTO.getUserId() == null || bookmarkDTO.getContentId() == null) {
            throw new IllegalArgumentException("User ID and Content ID must not be null");
        }

        // UserEntity를 조회하여 확인
        UserEntity userEntity = userRepository.findById(bookmarkDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 북마크 추가
        BookmarkEntity bookmarkEntity = new BookmarkEntity();
        bookmarkEntity.setUserId(userEntity); // UserEntity 설정
        bookmarkEntity.setContentId(bookmarkDTO.getContentId());

        // 중복 검사 또는 유니크 체크
        if (bookmarkRepository.existsByUserIdAndContentId(userEntity, bookmarkDTO.getContentId())) {
            throw new IllegalArgumentException("Bookmark already exists");
        }

        bookmarkRepository.save(bookmarkEntity);
    }
}

