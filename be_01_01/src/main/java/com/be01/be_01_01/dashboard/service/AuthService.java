package com.be01.be_01_01.dashboard.service;

import com.be01.be_01_01.dashboard.config.security.JwtTokenProvider;
import com.be01.be_01_01.dashboard.repository.User.User;
import com.be01.be_01_01.dashboard.repository.User.UserJpaRepository;
import com.be01.be_01_01.dashboard.dto.auth.Login;
import com.be01.be_01_01.dashboard.dto.auth.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserJpaRepository userJpaRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional(transactionManager = "tmJpa")
    public boolean signUp(SignUp signUpRequest) {
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();
        String username = signUpRequest.getName();
        String phoneNum = signUpRequest.getPhoneNum();
        String role = signUpRequest.getRole();

        // 서버에서도 유효성 검사 추가
        if (username == null || username.isEmpty()) {
            return false; // 이름이 없으면 회원가입 실패
        }


        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);


        //  유저가 있으면 ID 만 등록 아니면 유저도 만들기
        User userFound = userJpaRepository.findByNameAndEmail(username,email).orElseGet(() ->
                userJpaRepository.save(User.builder()
                        .Author(username)
                        .email(email)
                        .password(encodedPassword) // 암호화된 비밀번호 저장
                        .phoneNum(phoneNum)
                        .role(role)
                        .build())
        );

        return true;
    }

    public String login(Login loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // 인증 시도
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT 토큰 생성
        return jwtTokenProvider.createToken(email,getUserRoles(email));
    }

    private List<String> getUserRoles(String email) {

        return Collections.singletonList("USER");
    }

    public String createToken(String email) {
        String token = jwtTokenProvider.createToken(email); // 토큰 생성 및 변수에 저장
        return token; // 생성된 토큰 반환
    }

}


