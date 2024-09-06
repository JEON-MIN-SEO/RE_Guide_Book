package guide_book_2.KTO_public_api_2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "days")
public class DayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "day_number")
    private int dayNumber;

    @ManyToOne(fetch = FetchType.LAZY) //（関連したEntityを実際利用する前にはロードしない）
    @JoinColumn(name = "guidebook_id")
    private GuidebookEntity guidebookId;

    @Column(name = "content_Json",columnDefinition = "TEXT")
    private String contentJson;
}
