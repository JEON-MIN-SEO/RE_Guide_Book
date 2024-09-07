package guide_book_2.KTO_public_api_2.repository;

import guide_book_2.KTO_public_api_2.entity.DayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<DayEntity, Long> {
}
