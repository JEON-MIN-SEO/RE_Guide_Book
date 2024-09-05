package guide_book_2.KTO_public_api_2.service;

import guide_book_2.KTO_public_api_2.dto.UserDTO;
import guide_book_2.KTO_public_api_2.entity.UserEntity;
import guide_book_2.KTO_public_api_2.enums.ProviderEnums;
import guide_book_2.KTO_public_api_2.error.CustomException;
import guide_book_2.KTO_public_api_2.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //@Transactional
    public void JoinProcess(UserDTO userDTO) {
        //DBへ同じユーザがあるか確認するメソッドが必要、UserRepositoryでexistsByメソッドを使う
        if (userRepository.existsByUserEmail(userDTO.getUserEmail())) {
            throw new CustomException(1001, "すでに存在するEmailです"); // 例外処理または好きな方法で対応
//            같은 이메일로 로그인하면 위 에러 메세지가 출력되지 않는다. 컨트롤러에서 다시 저걸 가지고 출력해야함
        } else {
            //DTOをEntityに変換
            UserEntity data = new UserEntity();
            data.setUserEmail(userDTO.getUserEmail());
            data.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            data.setProvider(ProviderEnums.LOCAL);
            userRepository.save(data);
        }
    }
}
/* 로컬회원가입, 간편회원가입에 각자 null인지 체크하는 로직

public void JoinProcess(@Valid UserDTO userDTO) {
    if (userDTO.getProvider() == ProviderEnums.LOCAL) {
        // 로컬 로그인일 경우 이메일과 비밀번호가 필수
        if (userDTO.getUserEmail() == null || userDTO.getPassword() == null) {
            throw new IllegalArgumentException("Email and password must be provided for local login");
        }
    } else if (userDTO.getProvider() == ProviderEnums.SOCIAL) {
        // 소셜 로그인일 경우 lineId가 필수
        if (userDTO.getLineId() == null) {
            throw new IllegalArgumentException("Line ID must be provided for social login");
        }
    } else {
        throw new IllegalArgumentException("Invalid provider");
    }

    // Save user logic
}*/
