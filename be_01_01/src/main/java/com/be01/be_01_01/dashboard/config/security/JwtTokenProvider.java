package com.be01.be_01_01.dashboard.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret-key-source}") // application.yaml 에서 jwt.secret-key-source 값을 가져옴
    private String secretKeySource;
    private String secretKey;


    private final UserDetailsService userDetailsService; // 사용자 정보를 로드하는 서비스
    @PostConstruct // 빈 초기화 후 실행될 메서드
    public void setUp(){
        secretKey = Base64.getEncoder()
                .encodeToString(secretKeySource.getBytes()); // 비밀키를 Base64로 인코딩
    }

    private long tokenValidMillisecond = 1000L * 60 * 60; // 1시간


    // HTTP 요청에서 토큰을 가져오는 메서드
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
        // 요청 헤더에서 "X-AUTH-TOKEN" 값을 가져옴
    }

    // 토큰을 생성하는 메서드 (역할 정보 포함)
    public String createToken(String email, List<String> roles) {
        Claims claims = Jwts.claims()
                .setSubject(email); // 이메일을 주제로 설정
        claims.put("roles", roles); // 역할 정보 추가
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) // 발행 시간 설정
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond)) // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) // HS256 알고리즘과 비밀키를 사용하여 서명
                .compact(); // JWT 생성
    }

    // 토큰을 생성하는 메서드 (역할 정보 미포함)
    public String createToken(String email) { // 위 메서드와 유사, 역할 정보는 포함하지 않음
        Date now = new Date();

        return Jwts.builder()
                .setSubject(email) // 토큰의 주제(subject)로 이메일을 설정
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond)) // 토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) // HS256 알고리즘과 비밀키를 사용하여 서명
                .compact(); // JWT 생성
    }

    // JWT 토큰의 유효성을 검증하는 메소드
    public boolean validateToken(String jwtToken) {
        try {
            // JWT 토큰을 파싱해서 Claims 객체를 얻어옴
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey) // 서명 키 설정
                    .parseClaimsJws(jwtToken)
                    // 토큰 파싱
                    .getBody(); // Payload 부분(클레임)을 가져옴
            Date now = new Date(); // 현재 시간
            // 토큰의 만료 시간이 현재 시간보다 이후인지 확인
            return claims.getExpiration()
                    .after(now);
        } catch (Exception e) { // 파싱 중 예외 발생 시 false 반환
            return false;
        }
    }

    // JWT 토큰을 사용해서 사용자의 Authentication 객체를 리턴하는 메소드
    public Authentication getAuthentication(String jwtToken) {
        // JWT 토큰에서 사용자 이메일(사용자 이름)을 추출
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserEmail(jwtToken));
        // UserDetails를 기반으로 UsernamePasswordAuthenticationToken 생성 및 반환
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // JWT 토큰에서 사용자의 이메일(주제)를 추출하는 private 메소드
    private String getUserEmail(String jwtToken) {
        return Jwts.parser()
                // 토큰 파서 생성
                .setSigningKey(secretKey)
                // 서명 키 설정
                .parseClaimsJws(jwtToken)
                // 토큰 파싱
                .getBody()
                // Payload 부분(클레임)을 가져옴
                .getSubject();
                // 클레임에서 subject(이메일) 추출
    }
}
