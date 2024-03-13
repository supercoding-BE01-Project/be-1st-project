package com.be01.be_01_01.dashboard.web.controller;

import com.be01.be_01_01.dashboard.service.AuthService;
import com.be01.be_01_01.dashboard.web.auth.Login;
import com.be01.be_01_01.dashboard.web.auth.Response;
import com.be01.be_01_01.dashboard.web.auth.SignUp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/sign")
public class SignController {

    private final AuthService authService;

    @ApiOperation("회원가입")
    @PostMapping(value = "/register")
    public ResponseEntity<Response> register(@RequestBody SignUp signUpRequest) {

        boolean isSuccess = authService.signUp(signUpRequest);

        if (isSuccess) {
            // JWT 토큰 생성 로직을 signUp 메소드 내부 또는 별도의 메소드로 구현
            String jwtToken = authService.createToken(signUpRequest.getEmail()); //

            Response response = new Response();
            response.setStatus(200);
            response.setMessage("회원가입 성공");
            response.setJwtToken(jwtToken);

            return ResponseEntity.ok(response); // 200 OK 상태와 함께 ApiResponse 객체 반환
        } else {
            Response response = new Response();
            response.setStatus(400); // 또는 적절한 오류 코드
            response.setMessage("회원가입 실패");
            response.setJwtToken(null);

            return ResponseEntity.badRequest().body(response); // 400 Bad Request 상태와 함께 ApiResponse 객체 반환
        }
    }

    @ApiOperation("로그인")
    @PostMapping(value = "/login")
    public String login(@RequestBody Login loginRequest, HttpServletResponse httpServletResponse) {

        String token = authService.login(loginRequest);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        httpServletResponse.setHeader("X-AUTH-TOKEN", token);
        return userEmail+"님이 로그인에 성공하였습니다.";
    }

    @PostMapping("/logout")
    public String logout() {
        // 현재 사용자 로그아웃 처리

        SecurityContextHolder.clearContext();
        return "로그아웃되었습니다.";
    }
}