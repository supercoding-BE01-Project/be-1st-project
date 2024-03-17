package com.be01.be_01_01.dashboard.web.controller;

import com.be01.be_01_01.dashboard.dto.*;
import com.be01.be_01_01.dashboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;



    // 게시물 전체 조회 API
    @GetMapping("/posts")
    public ResponseEntity<?> findAllPosts() {
        List<PostsResponseDTO> posts = postService.findAllPosts();
        return ResponseEntity.ok().body(Map.of("posts", posts));
    }

    // 게시물 이메일 통해서 조회 API
    @GetMapping("/posts/search")
    public ResponseEntity<?> findPostsByEmail(@RequestParam("author_email") String email) {
        List<PostsResponseDTO> posts = postService.findPostsByEmail(email);
        return ResponseEntity.ok().body(Map.of("posts", posts));
    }

    // 댓글 조회 API
    @GetMapping("/comments")
    public ResponseEntity<?> findAllComments() {
        List<CommentsResponseDTO> comments = postService.findAllComments();
        return ResponseEntity.ok().body(Map.of("comments", comments));
    }

    // 게시물 생성 API
    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@RequestBody CreatePostDTO createPostDTO) {
        try {
            postService.createPost(createPostDTO);
            return ResponseEntity.ok(Collections.singletonMap("message", "게시물이 성공적으로 작성되었습니다."));
        } catch (IllegalArgumentException e) {
            // 유저 또는 입력된 데이터가 유효하지 않을 때 예외 처리
            logger.error("유저 또는 입력 데이터가 유효하지 않습니다.", e);
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            // 기타 예외 처리
            logger.error("기타 예외가 발생했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    // 댓글 생성 API
    @PostMapping("/comments")
    public ResponseEntity<?> createComment(@RequestBody CreateCommentDTO createCommentDTO) {
        try {
            postService.createComment(createCommentDTO);
            return ResponseEntity.ok(Collections.singletonMap("message", "댓글이 성공적으로 작성되었습니다."));
        } catch (IllegalArgumentException e) {
            // 유저 또는 입력된 데이터가 유효하지 않을 때 예외 처리
            logger.error("유저 또는 입력 데이터가 유효하지 않습니다.", e);
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            // 기타 예외 처리
            logger.error("기타 예외가 발생했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    // 게시글 수정 API
    @PutMapping("/posts/{postId}/update")
    public ResponseEntity<?> updatePost(@PathVariable Integer postId, @RequestBody UpdatePostDTO updatePostDTO) {
        try {
            postService.updatePost(postId, updatePostDTO);
            return ResponseEntity.ok(Collections.singletonMap("message", "게시글이 성공적으로 수정되었습니다."));
        } catch (IllegalArgumentException e) {
            logger.error("유저 또는 입력 데이터가 유효하지 않습니다.", e);
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            // 기타 예외 처리
            logger.error("기타 예외가 발생했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    // 댓글 수정 API
    @PutMapping("/comments/{commentId}/update")
    public ResponseEntity<?> updateComment(@PathVariable Integer commentId, @RequestBody UpdateCommentDTO updateCommentDTO) {
        try {
            postService.updateComment(commentId, updateCommentDTO);
            return ResponseEntity.ok(Collections.singletonMap("message", "댓글이 성공적으로 수정되었습니다."));
        } catch (IllegalArgumentException e) {
            logger.error("유저 또는 입력 데이터가 유효하지 않습니다.", e);
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            // 기타 예외 처리
            logger.error("기타 예외가 발생했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    // 게시글 삭제 API
    @DeleteMapping("/posts/{postId}/delete")
    public ResponseEntity<?> deletePost(@PathVariable Integer postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.ok(Collections.singletonMap("message", "게시글이 성공적으로 삭제되었습니다."));
        } catch (IllegalArgumentException e) {
            logger.error("유저 또는 입력 데이터가 유효하지 않습니다.", e);
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            // 기타 예외 처리
            logger.error("기타 예외가 발생했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    // 댓글 삭제 API
    @DeleteMapping("/comments/{commentId}/delete")
    public ResponseEntity<?> deleteComment(@PathVariable Integer commentId) {
        try {
            postService.deleteComment(commentId);
            return ResponseEntity.ok(Collections.singletonMap("message", "댓글이 성공적으로 삭제되었습니다."));
        } catch (IllegalArgumentException e) {
            logger.error("유저 또는 입력 데이터가 유효하지 않습니다.", e);
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            // 기타 예외 처리
            logger.error("기타 예외가 발생했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", e.getMessage()));
        }
    }
}
