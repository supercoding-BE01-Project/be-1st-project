package com.be01.be_01_01.dashboard;

import com.be01.be_01_01.dashboard.dto.DashboardPostsDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DashboardController {

    // 게시물 전체 조회 API

    // 게시물 생성 API
    @PostMapping("/api/posts")
    @ResponseBody
    public String create(DashboardPostsDTO dashboardPostsDTO) {
        return "게시물이 성공적으로 작성되었습니다.";
    }
}
