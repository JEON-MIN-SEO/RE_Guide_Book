package guide_book_2.KTO_public_api_2.dto;

import guide_book_2.KTO_public_api_2.enums.ProviderEnums;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String userEmail;

    private String password;

    private ProviderEnums provider;

    private String lineId;
}