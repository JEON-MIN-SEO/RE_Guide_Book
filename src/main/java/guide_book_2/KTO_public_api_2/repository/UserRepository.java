package guide_book_2.KTO_public_api_2.repository;

import guide_book_2.KTO_public_api_2.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //同じユーザがあるか確認するため
    boolean existsByUserEmail(String userEmail);
    //
     UserEntity findByUserEmail(String userEmail);
}
