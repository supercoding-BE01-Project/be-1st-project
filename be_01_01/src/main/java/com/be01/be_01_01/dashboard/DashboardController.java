package com.be01.be_01_01.dashboard;

import com.be01.be_01_01.dashboard.dto.DashboardPostsDTO;
import com.be01.be_01_01.dashboard.entity.Board;
import com.be01.be_01_01.dashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class DashboardController {
    private final DashboardService dashboardService;

    @Autowired
    private DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // 게시물 전체 조회 API

    // 게시물 생성 API
    @PostMapping("/posts")
    public ResponseEntity<Board> createPost(@RequestBody DashboardPostsDTO dashboardPostsDTO) {
        try {
            Board createBoard = dashboardService.postsBoard(dashboardPostsDTO);
            return new ResponseEntity<>(createBoard, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // 유저 또는 입력된 데이터가 유효하지 않을 때 예외 처리
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // 기타 예외 처리
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
