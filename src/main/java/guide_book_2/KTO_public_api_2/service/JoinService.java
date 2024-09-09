package guide_book_2.KTO_public_api_2.service;

import guide_book_2.KTO_public_api_2.dto.UserDTO;
import guide_book_2.KTO_public_api_2.entity.UserEntity;
import guide_book_2.KTO_public_api_2.enums.ProviderEnums;
import guide_book_2.KTO_public_api_2.error.CustomException;
import guide_book_2.KTO_public_api_2.repository.UserRepository;
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
    //로컬 회원가입, 라인 회원가입이 둘 다 들어가야 할 듯 하다.
    public void JoinProcess(UserDTO userDTO) {
        // 이메일 유효성 검사: null 또는 빈 값인지 확인
        if (userDTO.getUserEmail() == null || userDTO.getUserEmail().isEmpty()) {
            throw new CustomException(1001, "Emailは必須項目です");
        }

        // 비밀번호 유효성 검사: null 또는 빈 값인지 확인
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new CustomException(1002, "Passwordは必須項目です");
        }

        //DBへ同じユーザがあるか確認するメソッドが必要、UserRepositoryでexistsByメソッドを使う
        if (userRepository.existsByUserEmail(userDTO.getUserEmail())) {
            throw new CustomException(1003, "すでに存在するEmailです"); // 例外処理、エラコード、メッセージ
        } else {
            UserEntity data = new UserEntity();
            data.setUserEmail(userDTO.getUserEmail());
            data.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            //data.setProvider(null);
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
