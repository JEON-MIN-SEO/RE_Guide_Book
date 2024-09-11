package guide_book_2.KTO_public_api_2.repository;

import guide_book_2.KTO_public_api_2.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // 이메일로 사용자 존재 여부 확인
    boolean existsByUserEmail(String userEmail);

    // 이메일로 사용자 찾기
    UserEntity findByUserEmail(String userEmail);

    // 라인 아이디로 사용자 찾기
    UserEntity findByLineId(String lineId);
}

