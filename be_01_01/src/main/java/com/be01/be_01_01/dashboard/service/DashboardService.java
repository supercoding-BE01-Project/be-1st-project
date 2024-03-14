package com.be01.be_01_01.dashboard.service;

import com.be01.be_01_01.dashboard.dto.*;
import com.be01.be_01_01.dashboard.entity.*;
import com.be01.be_01_01.dashboard.repository.BoardRepository;
import com.be01.be_01_01.dashboard.repository.CommentRepository;
import com.be01.be_01_01.dashboard.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final BoardRepository boardRepository;
    private final UsersRepository usersRepository;
    private final CommentRepository commentRepository;

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
    //게시판 수정
    @Transactional
    public void updateBoard(UpdateBoardDto dto) {
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException("해당 게시물을 찾을 수 없습니다. Board ID: " + dto.getBoardId()));

        if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
            board.setTitle(dto.getTitle());
        }
        if (dto.getContent() != null && !dto.getContent().isEmpty()) {
            board.setContent(dto.getContent());
        }
        boardRepository.save(board);
    }

    //댓글 수정
    @Transactional
    public void updateComment(UpdateCommentDto dto) {
        Comment comment = commentRepository.findById(dto.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다. Comment ID: " + dto.getCommentId()));

        if (dto.getContent() != null && !dto.getContent().isEmpty()) {
            comment.setContent(dto.getContent());
        }
        commentRepository.save(comment);
    }

    //게시판 삭제
    @Transactional
    public void deleteBoard(Integer boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("해당 Id로 작성한 게시물을 찾을 수 없습니다.: " + boardId));
        boardRepository.delete(board);
    }

    //댓글 삭제
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("해당 Id로 작성한 댓글을 찾을 수 없습니다.: " + commentId));
        commentRepository.delete(comment);
    }

}

