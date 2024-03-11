package com.be01.be_01_01.dashboard;

import com.be01.be_01_01.dashboard.dto.DashboardPostsDTO;
import com.be01.be_01_01.dashboard.entity.Board;
import com.be01.be_01_01.dashboard.entity.Comment;
import com.be01.be_01_01.dashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<?> createBoard(@RequestBody DashboardPostsDTO dashboardPostsDTO) {
        try {
            dashboardService.createBoard(dashboardPostsDTO);
            Map<String, String> response = new HashMap<>();
            response.put("message", "게시물이 성공적으로 작성되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // 유저 또는 입력된 데이터가 유효하지 않을 때 예외 처리
            return new ResponseEntity<>("유저 또는 입력 데이터가 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // 기타 예외 처리
            return new ResponseEntity<>("기타 예외가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 댓글 생성 API
    @PostMapping("/comments")
    public ResponseEntity<?> createComment(@RequestBody DashboardPostsDTO dashboardPostsDTO) {
        try {
            Comment createComment = dashboardService.createComment(dashboardPostsDTO);
            Map<String, String> response = new HashMap<>();
            response.put("message", "댓글이 성공적으로 작성되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (IllegalArgumentException e) {
            // 유저 또는 입력된 데이터가 유효하지 않을 때 예외 처리
            return new ResponseEntity<>("유저 또는 입력 데이터가 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // 기타 예외 처리
            return new ResponseEntity<>("기타 예외가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
