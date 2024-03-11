package com.be01.be_01_01.dashboard.service;

import com.be01.be_01_01.dashboard.dto.DashboardPostsDTO;
import com.be01.be_01_01.dashboard.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final BoardRepository boardRepository;
    private final UsersRepository usersRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public DashboardService(BoardRepository boardRepository, UsersRepository usersRepository, CommentRepository commentRepository) {
        this.boardRepository = boardRepository;
        this.usersRepository = usersRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Board createBoard(DashboardPostsDTO dashboardPostsDTO) {

        Users users = usersRepository.findById(dashboardPostsDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. ID : " + dashboardPostsDTO.getUserId()));

        // 게시글 작성
        Board board = Board.builder()
                .users(users)
                .title(dashboardPostsDTO.getTitle())
                .content(dashboardPostsDTO.getContent())
                .build();

        return boardRepository.save(board);
    }

    @Transactional
    public Comment createComment(DashboardPostsDTO dashboardPostsDTO) {

        Users users = usersRepository.findById(dashboardPostsDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. ID : " + dashboardPostsDTO.getUserId()));
        Board board = boardRepository.findById(dashboardPostsDTO.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID : " + dashboardPostsDTO.getBoardId()));

        // 댓글 작성
        Comment comment = Comment.builder()
                .users(users)
                .board(board)
                .content(dashboardPostsDTO.getContent())
                .build();

        return commentRepository.save(comment);
    }
}
