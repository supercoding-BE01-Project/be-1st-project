package com.be01.be_01_01.dashboard.web;

import com.be01.be_01_01.dashboard.config.security.JwtTokenProvider;
import com.be01.be_01_01.dashboard.service.AuthService;
import com.be01.be_01_01.dashboard.service.UserService;
import com.be01.be_01_01.dashboard.dto.auth.Login;
import com.be01.be_01_01.dashboard.dto.auth.Response;
import com.be01.be_01_01.dashboard.dto.auth.SignUp;
import com.be01.be_01_01.dashboard.dto.UserPostsDTO;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/sign")
public class SignController {

    private final AuthService authService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;



    //전체 회원 정보 확인
    @ApiOperation(value = "모든 회원을 조회(유저id PK,이메일,핸드폰 번호)")
    @GetMapping("/logininfos")
    public List<UserPostsDTO> userInfos(){
        return userService.findAllUserInfos();
    }

    @ApiOperation("회원가입")
    @PostMapping(value = "/register")
    public ResponseEntity<Response> register(@RequestBody SignUp signUpRequest) {

        boolean isSuccess = authService.signUp(signUpRequest);

        if (isSuccess) {
            Response response = new Response();
            response.setStatus(200);
            response.setMessage("회원가입 성공");

            return ResponseEntity.ok(response); // 200 OK 상태와 함께 ApiResponse 객체 반환
        } else {
            Response response = new Response();
            response.setStatus(400); // 또는 적절한 오류 코드
            response.setMessage("회원가입 실패");

            return ResponseEntity.badRequest().body(response); // 400 Bad Request 상태와 함께 ApiResponse 객체 반환
        }
    }

    @ApiOperation("로그인")
    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody Login loginRequest, HttpServletResponse httpServletResponse) {

        // 로그인 시도
        String userEmail = authService.login(loginRequest);

        // 토큰 생성 및 설정
        String token = jwtTokenProvider.createToken(userEmail);
        httpServletResponse.setHeader("X-AUTH-TOKEN", token);

        // 로그인 성공 응답 반환
        return ResponseEntity.ok("로그인에 성공하였습니다.");
    }

    @PostMapping("/logout")
    public String logout() {
        // 현재 사용자 로그아웃 처리

        SecurityContextHolder.clearContext();
        return "로그아웃되었습니다.";
    }
}