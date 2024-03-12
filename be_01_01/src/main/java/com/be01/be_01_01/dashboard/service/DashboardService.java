package com.be01.be_01_01.dashboard.service;

import com.be01.be_01_01.dashboard.dto.BoardResponseDTO;
import com.be01.be_01_01.dashboard.dto.CommentResponseDTO;
import com.be01.be_01_01.dashboard.dto.CreateBoardDTO;
import com.be01.be_01_01.dashboard.dto.CreateCommentDTO;
import com.be01.be_01_01.dashboard.entity.*;
import com.be01.be_01_01.dashboard.repository.BoardRepository;
import com.be01.be_01_01.dashboard.repository.CommentRepository;
import com.be01.be_01_01.dashboard.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public Board createBoard(CreateBoardDTO createBoardDTO) {

        Users users = usersRepository.findById(createBoardDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. ID : " + createBoardDTO.getUserId()));

        // 게시글 작성
        Board board = Board.builder()
                .users(users)
                .title(createBoardDTO.getTitle())
                .content(createBoardDTO.getContent())
                .build();

        return boardRepository.save(board);
    }

    @Transactional
    public Comment createComment(CreateCommentDTO createCommentDTO) {

        Users users = usersRepository.findById(createCommentDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. ID : " + createCommentDTO.getUserId()));
        Board board = boardRepository.findById(createCommentDTO.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID : " + createCommentDTO.getBoardId()));

        // 댓글 작성
        Comment comment = Comment.builder()
                .users(users)
                .board(board)
                .content(createCommentDTO.getContent())
                .build();

        return commentRepository.save(comment);
    }

    @Transactional
    public List<BoardResponseDTO> findAllBoards() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(board -> new BoardResponseDTO(
                board.getBoardId(),
                board.getTitle(),
                board.getContent(),
                board.getUsers().getName(),
                board.getCreatedAt())).collect(Collectors.toList());
    }

    public List<BoardResponseDTO> findBoardsByEmail(String email) {
        List<Board> boards = boardRepository.findByUsersEmail(email);
        return boards.stream().map(board -> new BoardResponseDTO(
                board.getBoardId(),
                board.getTitle(),
                board.getContent(),
                board.getUsers().getName(),
                board.getCreatedAt())).collect(Collectors.toList());
    }

    public List<CommentResponseDTO> findAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(comment -> new CommentResponseDTO(
                comment.getCommentId(),
                comment.getContent(),
                comment.getUsers().getName(),
                comment.getBoard().getBoardId(),
                comment.getCreatedAt())).collect(Collectors.toList());
    }
}
