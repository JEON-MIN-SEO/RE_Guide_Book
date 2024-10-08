package guide_book_2.KTO_public_api_2.service;

import guide_book_2.KTO_public_api_2.dto.CustomOAuth2User;
import guide_book_2.KTO_public_api_2.dto.LineResponse;
import guide_book_2.KTO_public_api_2.dto.OAuth2Response;
import guide_book_2.KTO_public_api_2.dto.UserDTO;
import guide_book_2.KTO_public_api_2.entity.UserEntity;
import guide_book_2.KTO_public_api_2.enums.ProviderEnums;
import guide_book_2.KTO_public_api_2.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("CustomOAuthUserService.loadUser");

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        System.out.println(registrationId);
        System.out.println(userNameAttributeName);

        OAuth2Response oAuth2Response = null;
        //어느 플랫폼 정보인지 확인
        if (registrationId.equals("line")) {


            oAuth2Response = new LineResponse(oAuth2User.getAttributes());
        }else {

            return null;
        }

        System.out.println("LINE 사용자 정보 응답: " + oAuth2User.getAttributes());

        //리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만듬
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        System.out.println(username);

        //UserEntity existData = userRepository.findByLineId(username);
        // 사용자 검색 부분 수정
        UserEntity existData = userRepository.findByLineId(oAuth2Response.getProviderId());


        if (existData == null) {

            UserEntity userEntity = new UserEntity();
            userEntity.setLineId(username);
            userEntity.setProvider(ProviderEnums.LINE);

            userRepository.save(userEntity);

            UserDTO userDTO = new UserDTO();
            userDTO.setLineId(username);
            userDTO.setProvider(ProviderEnums.LINE);

            return new CustomOAuth2User(userDTO, oAuth2User.getAttributes());
        }
        else {
            existData.setLineId(oAuth2Response.getProviderId());

            userRepository.save(existData);

            UserDTO userDTO = new UserDTO();
            userDTO.setLineId(oAuth2Response.getProviderId());
            userDTO.setProvider(ProviderEnums.LINE);

            return new CustomOAuth2User(userDTO, oAuth2User.getAttributes());
        }
    }
}
