package guide_book_2.KTO_public_api_2.repository;

import guide_book_2.KTO_public_api_2.entity.GuidebookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuidebookRepository extends JpaRepository<GuidebookEntity, Long> {
}
