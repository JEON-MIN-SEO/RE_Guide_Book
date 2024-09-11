package guide_book_2.KTO_public_api_2.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {

        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public Long getUserId(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userId", Long.class);
    }

    public String getUserLineId(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("userId", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String createJwt(Long userId, Long expiredMs) { //String role

        long expirationTimeMs = 604800000L; // 7 days in milliseconds

        return Jwts.builder()
                .claim("userId", userId)
                //.claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) //현재 발행 시간
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) //언제 소멸 할건지
                .signWith(secretKey) //암호화
                .compact(); //토큰 컴팩
    }

    public String createLineJwt(String userId, Long expiredMs) { //String role

        long expirationTimeMs = 604800000L; // 7 days in milliseconds

        return Jwts.builder()
                .claim("userId", userId)
                //.claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) //현재 발행 시간
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) //언제 소멸 할건지
                .signWith(secretKey) //암호화
                .compact(); //토큰 컴팩
    }
}
