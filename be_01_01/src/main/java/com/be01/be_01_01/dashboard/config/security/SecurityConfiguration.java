package com.be01.be_01_01.dashboard.config.security;

import com.be01.be_01_01.dashboard.web.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    // JWT 토큰 제공자를 주입받는 필드
    private final JwtTokenProvider jwtTokenProvider;

    // HttpSecurity를 사용하여 웹 보안을 구성하는 메소드
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // 요청에 대한 접근 권한을 설정
                .requestMatchers("/api/sign/register", "/api/sign/login").permitAll() // 회원가입, 로그인 경로는 모두에게 허용
                .anyRequest().authenticated() // 그 외 모든 요청은 인증을 필요로 함
                .and()
                .logout()
                // 로그아웃 설정
                .logoutUrl("/api/sign/logout") // 로그아웃 처리 URL
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID")  // 쿠키 삭제
                .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트 URL
                .permitAll()
                .and()
                .csrf().disable(); // CSRF 보호 기능 비활성화
    }

    // SecurityFilterChain 빈을 등록하여 HTTP 요청에 대한 보안 필터 체인을 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())
                // CORS 설정
                .and()
                .csrf().disable() // CSRF 보호 기능 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 세션 정책을 상태 없음으로 설정
                .and()
                .authorizeRequests()
                // 요청에 대한 접근 권한 설정
                .requestMatchers("/resources/static/**", "/api/*/*").permitAll() // 정적 자원 및 특정 API 경로는 모두에게 허용
                .requestMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui/**", "/webjars/**","/swagger/**").permitAll() // Swagger 문서 관련 경로는 모두에게 허용
                .anyRequest().authenticated() // 그 외 모든 요청은 인증을 필요로 함
                .anyRequest().authenticated() // 그 외 모든 요청은 인증을 필요로 함
                .and()
                .exceptionHandling()
                // 예외 처리 설정
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증 실패 시 처리를 위한 진입점 설정
                .accessDeniedHandler(new CustomerAccessDeniedHandler()) // 접근 거부 처리 핸들러 설정
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // JWT 인증 필터 추가

        // 기타 필요한 설정 추가

        return http.build(); // HttpSecurity 설정을 기반으로 SecurityFilterChain 객체 생성 및 반환
    }

    // AuthenticationManager 빈을 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // AuthenticationManager를 반환
    }
    // CORS 설정을 위한 CorsConfigurationSource 빈을 등록
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // CORS 설정을 위한 새로운 CorsConfiguration 객체 생성
        configuration.setAllowedOrigins(List.of("http://localhost:63342"));
        // "http://localhost:63342"을 요청을 허용할 출처(origin)로 설정
        configuration.setAllowCredentials(true);
        // 쿠키를 포함한 요청을 허용하도록 설정
        configuration.addAllowedHeader("*");
        // 모든 헤더를 요청 허용 헤더로 설정
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS"));
        // "GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS" 메소드를 요청 허용 메소드로 설정
        configuration.setMaxAge(3600L);
        // pre-flight 요청의 결과를 3600초(1시간) 동안 캐시할 수 있도록 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // CORS 설정을 URL 기반으로 관리할 수 있는 UrlBasedCorsConfigurationSource 객체 생성
        source.registerCorsConfiguration("/**", configuration);
        // 모든 경로("/**")에 대해 위에서 정의한 CORS 설정(configuration)을 적용
        return source;
        // 설정이 완료된 CORS 구성 소스 객체를 반환
    }
}
