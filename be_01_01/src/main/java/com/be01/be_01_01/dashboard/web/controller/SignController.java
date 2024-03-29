package com.be01.be_01_01.dashboard.web.controller;

import com.be01.be_01_01.dashboard.config.security.JwtTokenProvider;
import com.be01.be_01_01.dashboard.service.AuthService;
import com.be01.be_01_01.dashboard.service.UserService;
import com.be01.be_01_01.dashboard.dto.auth.Login;
import com.be01.be_01_01.dashboard.dto.auth.Response;
import com.be01.be_01_01.dashboard.dto.auth.SignUp;
import com.be01.be_01_01.dashboard.dto.UserPostsDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/sign")
public class SignController {

    private final AuthService authService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;



    //전체 회원 정보 확인
    @Operation(summary = "모든 회원을 조회(유저id PK,이메일)")
    @GetMapping("/logininfos")
    public List<UserPostsDTO> userInfos(){
        return userService.findAllUserInfos();
    }

    @Operation(summary = "회원가입")
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody SignUp signUpRequest) {

        boolean isSuccess = authService.signUp(signUpRequest);

        if (isSuccess) {
            return ResponseEntity.ok(Collections.singletonMap("message", "회원가입이 완료되었습니다."));
        } else {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "회원가입에 실패했습니다."));
        }
    }

    @Operation(summary = "로그인")
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody Login loginRequest, HttpServletResponse httpServletResponse) {

        // 로그인 시도
        String userEmail = authService.login(loginRequest);

        // 토큰 생성 및 설정
        String token = jwtTokenProvider.createToken(userEmail);
        httpServletResponse.setHeader("Authorization", "Bearer " + token);

        // 로그인 성공 응답 반환
        return ResponseEntity.ok(Collections.singletonMap("message", "로그인이 성공적으로 완료되었습니다."));
    }

    @PostMapping("/logout")
    public String logout() {
        // 현재 사용자 로그아웃 처리

        SecurityContextHolder.clearContext();
        return "로그아웃되었습니다.";
    }
}