package com.be01.be_01_01.dashboard.web.controller;

import com.be01.be_01_01.dashboard.web.dto.DashboardPostsDTO;
import com.be01.be_01_01.dashboard.service.UserService;
import com.be01.be_01_01.dashboard.web.dto.UserLoginDTO;
import com.be01.be_01_01.dashboard.web.dto.UserPostsDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final UserService userService;

    //회원 가입 API


    //전체 회원 정보 확인
    @ApiOperation(value = "모든 회원을 조회(유저id PK,이메일,핸드폰 번호)")
    @GetMapping("/api/logininfos")
    public List<UserPostsDTO> userInfos(){
        return userService.findAllUserInfos();
    }




    // 게시물 전체 조회 API

    // 게시물 생성 API
    @PostMapping("/api/posts")
    @ResponseBody
    public String create(DashboardPostsDTO dashboardPostsDTO) {
        return "게시물이 성공적으로 작성되었습니다.";
    }
}
