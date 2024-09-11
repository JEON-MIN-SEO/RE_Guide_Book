package guide_book_2.KTO_public_api_2.entity;

import guide_book_2.KTO_public_api_2.enums.ProviderEnums;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "user_email")
    private String userEmail;

    @Column(name = "password")
    private String password;

    @Column(name = "line_user_id")
    private String lineId;

    @Column(name = "provider")
    private ProviderEnums provider;
}