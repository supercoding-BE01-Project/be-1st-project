package com.be01.be_01_01.dashboard;

import com.be01.be_01_01.dashboard.dto.*;
import com.be01.be_01_01.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DashboardController {
    private final DashboardService dashboardService;

    // 게시물 전체 조회 API
    @GetMapping("/posts")
    public ResponseEntity<?> findAllBoards() {
        List<BoardResponseDTO> boards = dashboardService.findAllBoards();
        return ResponseEntity.ok().body(Map.of("posts", boards));
    }

    // 게시물 이메일 통해서 조회 API
    @GetMapping("/posts/search")
    public ResponseEntity<?> findBoardsByEmail(@RequestParam("author_email") String email) {
        List<BoardResponseDTO> boards = dashboardService.findBoardsByEmail(email);
        return ResponseEntity.ok().body(Map.of("posts", boards));
    }

    // 댓글 조회 API
    @GetMapping("/comments")
    public ResponseEntity<?> findAllComments() {
        List<CommentResponseDTO> comments = dashboardService.findAllComments();
        return ResponseEntity.ok().body(Map.of("comments", comments));
    }

    // 게시물 생성 API
    @PostMapping("/posts")
    public ResponseEntity<?> createBoard(@RequestBody CreateBoardDTO createBoardDTO) {
        try {
            dashboardService.createBoard(createBoardDTO);
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
    public ResponseEntity<?> createComment(@RequestBody CreateCommentDTO createCommentDTO) {
        try {
            dashboardService.createComment(createCommentDTO);
            Map<String, String> response = new HashMap<>();
            response.put("message", "댓글이 성공적으로 작성되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // 유저 또는 입력된 데이터가 유효하지 않을 때 예외 처리
            return new ResponseEntity<>("유저 또는 입력 데이터가 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // 기타 예외 처리
            return new ResponseEntity<>("기타 예외가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 게시글 수정 API
    @PutMapping("/{boardId}")
    public ResponseEntity<Void> updateBoard(@PathVariable Integer boardId, @RequestBody UpdateBoardDto dto) {
        dto.setBoardId(boardId);
        dashboardService.updateBoard(dto);
        return ResponseEntity.ok().build();
    }

    // 댓글 수정 API
    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Integer commentId, @RequestBody UpdateCommentDto dto) {
        dto.setCommentId(commentId);
        dashboardService.updateComment(dto);
        return ResponseEntity.ok().build();
    }

    // 게시글 삭제 API
    @DeleteMapping("/{boardId}")
    public String deleteBoardByPathBoardId(@PathVariable Integer boardId) {
        dashboardService.deleteBoard(boardId);
        return "삭제 완료";
    }

    // 댓글 삭제 API
    @DeleteMapping("/{commentId}")
    public String deleteCommentByPathCommentId(@PathVariable Integer commentId) {
        dashboardService.deleteComment(commentId);
        return "삭제 완료";
    }
}
