package project.ec2session.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKeySpec secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;
    private final String issuer;

    public JwtUtil(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.expiration.access-token}") long accessTokenExpiration,
            @Value("${jwt.expiration.refresh-token}") long refreshTokenExpiration,
            @Value("${jwt.issuer}") String issuer
    ) {
        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.issuer = issuer;
    }

    /**
     * 액세스 토큰을 생성하는 메서드
     *
     * @param userId        사용자 ID(pk)
     * @param username      username
     * @return              액세스 토큰
     */
    public String generateAccessToken(Long userId, String username) {
        return generateToken(userId, username, accessTokenExpiration);
    }

    /**
     * 리프레시 토큰을 생성하는 메서드
     *
     * @param userId        사용자 ID(pk)
     * @param username      username
     * @return              리프레시 토큰
     */
    public String generateRefreshToken(Long userId, String username) {
        return generateToken(userId, username, refreshTokenExpiration);
    }

    /**
     * JWT 토큰을 생성하는 메서드
     *
     * @param userId        사용자 ID(pk)
     * @param username      username
     * @param expiration    만료 기한
     * @return              토큰
     */
    private String generateToken(Long userId, String username, long expiration) {
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username", username)
                .issuer(issuer)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    /**
     * 토큰에서 사용자 ID(pk)를 추출하는 메서드
     *
     * @param token     토큰
     * @return          사용자 ID(pk)
     */
    public Long parseUserId(String token) {
        return Long.valueOf(parseToken(token).getSubject());
    }

    /**
     * 토큰에서 페이로드 클레임을 파싱하는 메서드
     *
     * @param token     토큰
     * @return          클레임
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
