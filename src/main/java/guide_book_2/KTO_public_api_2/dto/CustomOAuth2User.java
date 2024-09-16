package guide_book_2.KTO_public_api_2.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final UserDTO userDTO;
    private final Map<String, Object> attributes; // 원본 OAuth2 속성 저장

    public CustomOAuth2User(UserDTO userDTO, Map<String, Object> attributes) {
        this.userDTO = userDTO;
        this.attributes = attributes;
    }

    //사용자 Id 리턴
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    //role값 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return null;  // 이 부분은 다른 용도로 username을 반환
    }

    // LineUserId 반환하는 메서드 추가
    public String getLineUserId() {
        return userDTO.getLineId();
    }
}
