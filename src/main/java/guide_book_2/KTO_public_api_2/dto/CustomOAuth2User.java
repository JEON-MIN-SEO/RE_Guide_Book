package guide_book_2.KTO_public_api_2.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final UserDTO userDTO;

    public CustomOAuth2User(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    //사용자 Id 리턴
    @Override
    public Map<String, Object> getAttributes() {
        return null;
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
