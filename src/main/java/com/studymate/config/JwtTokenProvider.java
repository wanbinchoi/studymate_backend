package com.studymate.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;


    /**
     *Jwt 토큰 생성
     * @param userId 사용자 아이디
     * @return JWT 토큰
     */
    public String createToken(String userId){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getAccessTokenExpiration());

        return Jwts.builder()
                .setSubject(userId) //토큰의 주체(사용자 아이디)
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(expiration) //토큰 만료 시간
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) //서명
                .compact();
    }

    /**
     * JWT 토큰 검증
     * @param token JWT 토큰
     * @return 유효하면 true, 아니면 false
     */
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()) //비밀키로 검증
                    .build()
                    .parseClaimsJws(token); // 토큰 파싱
            return true; //검증 성공
        } catch(SecurityException | MalformedJwtException e) { //서명 불일치 | 잘못된 형식
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e){
            log.error("만료된 JWT 토큰입니다."); //만료
        } catch(UnsupportedJwtException e){
            log.error("지원되지 않는 JWT 토큰입니다.");
        } catch(IllegalArgumentException e){
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false; //검증 실패
    }

    /**
     * JWT 토큰에서 사용자 아이디 추출
     * @param token JWT 토큰
     * @return 사용자 아이디
     */
    public String getUserIdFromToken(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody(); // Payload 부분 가져오기

        return claims.getSubject(); // subject에서 userId 꺼내기
    }

    /**
     * 서명 키 생성
     * @return SecretKey
     */
    private SecretKey getSigningKey(){
        byte[] keyBytes = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
