package com.be01.be_01_01.dashboard.web.controller;

import com.be01.be_01_01.dashboard.service.AuthService;
import com.be01.be_01_01.dashboard.web.auth.Login;
import com.be01.be_01_01.dashboard.web.auth.SignUp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/sign")
public class SignController {

    private final AuthService authService;

    @ApiOperation("회원가입 확인")
    @PostMapping(value = "/register")
    public String register (@RequestBody SignUp signUpRequest){


        boolean isSuccess = authService.signUp(signUpRequest);
        return isSuccess ? "회원가입 성공하였습니다." : "회원가입 실패하였습니다.";


    }
    @ApiOperation("로그인")
    @PostMapping(value = "/login")
    public String login (@RequestBody Login loginRequest , HttpServletResponse httpServletResponse){
        String token = authService.login(loginRequest);
        httpServletResponse.setHeader("X-AUTH-TOKEN",token);
        return "로그인이 성공하였습니다.";
    }

    @PostMapping("/logout")
    public String logout() {
        // 현재 사용자 로그아웃 처리
        SecurityContextHolder.clearContext();
        return "로그아웃되었습니다.";
    }
}
