package guide_book_2.KTO_public_api_2.repository;

import guide_book_2.KTO_public_api_2.entity.DayEntity;
import guide_book_2.KTO_public_api_2.entity.GuidebookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DayRepository extends JpaRepository<DayEntity, Long> {
    List<DayEntity> findByGuidebook(GuidebookEntity guidebook);

    // GuidebookEntity와 dayNumber로 DayEntity를 조회하는 메서드
    Optional<DayEntity> findByGuidebookAndDayNumber(GuidebookEntity guidebook, int dayNumber);

    void deleteByGuidebook(GuidebookEntity guidebook);

}
