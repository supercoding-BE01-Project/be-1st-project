package com.be01.be_01_01.dashboard.web.controller;

import com.be01.be_01_01.dashboard.web.dto.DashboardPostsDTO;
import com.be01.be_01_01.dashboard.service.UserService;
import com.be01.be_01_01.dashboard.web.dto.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private UserService userService;

    //회원 가입 API
    @PostMapping("/api/login")
    public void signUp(UserLoginDTO userLoginDTO){

    }

    // 게시물 전체 조회 API

    // 게시물 생성 API
    @PostMapping("/api/posts")
    @ResponseBody
    public String create(DashboardPostsDTO dashboardPostsDTO) {
        return "게시물이 성공적으로 작성되었습니다.";
    }
}
