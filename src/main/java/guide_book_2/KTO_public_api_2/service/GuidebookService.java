package guide_book_2.KTO_public_api_2.service;

import guide_book_2.KTO_public_api_2.dto.DayDTO;
import guide_book_2.KTO_public_api_2.dto.GuidebookDTO;
import guide_book_2.KTO_public_api_2.entity.DayEntity;
import guide_book_2.KTO_public_api_2.entity.GuidebookEntity;
import guide_book_2.KTO_public_api_2.entity.UserEntity;
import guide_book_2.KTO_public_api_2.error.CustomException;
import guide_book_2.KTO_public_api_2.repository.DayRepository;
import guide_book_2.KTO_public_api_2.repository.GuidebookRepository;
import guide_book_2.KTO_public_api_2.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class GuidebookService {

    private final GuidebookRepository guidebookRepository;
    private final DayRepository dayRepository;
    private final UserRepository userRepository;

    public GuidebookService(GuidebookRepository guidebookRepository, DayRepository dayRepository, UserRepository userRepository) {
        this.guidebookRepository = guidebookRepository;
        this.dayRepository = dayRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createGuidebook(GuidebookDTO guidebookDTO) {
        UserEntity user = userRepository.findById(guidebookDTO.getUserId())
                .orElseThrow(() -> new CustomException(1002, "User not found"));

        GuidebookEntity guidebook = new GuidebookEntity();
        guidebook.setTitle(guidebookDTO.getTitle());
        guidebook.setDestination(guidebookDTO.getDestination());
        guidebook.setStartDate(guidebookDTO.getStartDate());
        guidebook.setEndDate(guidebookDTO.getEndDate());
        guidebook.setUserId(user);

        guidebookRepository.save(guidebook);

        // startDate와 endDate를 기반으로 DayEntity 생성
        createDaysForGuidebook(guidebook);
    }

    private void createDaysForGuidebook(GuidebookEntity guidebook) {
        LocalDate startDate = guidebook.getStartDate();
        LocalDate endDate = guidebook.getEndDate();

        //ChronoUnit.DAYS.betweenはjava 8から導入された’java.time'パッケージのChronoUnit Enumクラスに含まれたメソッドで
        //二つの日付けの間の計算をしてくれる　（８・２２〜８・３０＝８）
        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        // 해당 일수만큼 DayEntity 생성
        for (int i = 0; i < numberOfDays; i++) {
            DayEntity day = new DayEntity();
            day.setDayNumber(i + 1);  // dayNumber는 1부터 시작
            day.setGuidebook(guidebook);
            dayRepository.save(day);
        }
    }

    /*@Transactional
    public void addDayToGuidebook(Long guidebookId, DayDTO dayDTO) {
        GuidebookEntity guidebook = guidebookRepository.findById(guidebookId)
                .orElseThrow(() -> new IllegalArgumentException("Guidebook not found"));

        DayEntity day = new DayEntity();
        day.setDayNumber(dayDTO.getDayNumber());
        day.setGuidebook(guidebook);
        day.setContentJson(convertContentIdsToJson(dayDTO.getContentIds()));

        dayRepository.save(day);
    }

    private String convertContentIdsToJson(List<String> contentIds) {
        return String.join(",", contentIds);
    }*/
}
