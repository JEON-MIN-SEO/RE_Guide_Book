package guide_book_2.KTO_public_api_2.dto;

import guide_book_2.KTO_public_api_2.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override //사용자의 권한을 지정하고 null을 넣으면 NullPointerException이 발생할 수 있다.
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한이 없으므로 빈 리스트 반환
        return Collections.emptyList();
        /* 추후 권한을 설정하고 싶다면
        https://www.devyummi.com/page?id=668be4696a08359d16576c45 */
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUserEmail();
    }

    @Override //사용자의 아이디가 만료되었는지 확인
    public boolean isAccountNonExpired() {
        //return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override //사용자의 아이디가 잠겨있는지
    public boolean isAccountNonLocked() {
        //return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
        //return UserDetails.super.isEnabled();
        return true;
    }

    public UserEntity getUserEntity() {
        return this.userEntity; // UserEntity에서 직접 ID를 가져오기 위한 메서드
    }
}
