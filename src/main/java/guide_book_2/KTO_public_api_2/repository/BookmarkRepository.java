package guide_book_2.KTO_public_api_2.repository;

import guide_book_2.KTO_public_api_2.entity.BookmarkEntity;
import guide_book_2.KTO_public_api_2.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {
    // UserEntity와 contentId 조합의 북마크가 이미 존재하는지 확인하는 메소드
    boolean existsByUserIdAndContentId(UserEntity userId, String contentId);
    // 특정 유저의 북마크 목록 조회
    List<BookmarkEntity> findAllByUserId(UserEntity user);
}