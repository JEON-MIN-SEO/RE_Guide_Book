package guide_book_2.KTO_public_api_2.repository;

import guide_book_2.KTO_public_api_2.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUserEmail(String userEmail); //同じユーザがあるか確認するため
    //他にidを探すメッソどー必要
}
