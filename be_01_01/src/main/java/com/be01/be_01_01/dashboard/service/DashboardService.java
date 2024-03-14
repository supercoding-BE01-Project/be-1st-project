package com.be01.be_01_01.dashboard.service;

import com.be01.be_01_01.dashboard.dto.*;
import com.be01.be_01_01.dashboard.entity.*;
import com.be01.be_01_01.dashboard.repository.PostRepository;
import com.be01.be_01_01.dashboard.repository.CommentRepository;
import com.be01.be_01_01.dashboard.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DashboardService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Post createPost(CreatePostDTO createPostDTO) {

        // Optional.orElseThrow() 사용하여 존재여부만 확인하고 바로 처리
        User user = userRepository.findById(createPostDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. ID : " + createPostDTO.getUserId()));

        // 게시글 작성
        Post post = Post.builder()
                .user(user)
                .title(createPostDTO.getTitle())
                .content(createPostDTO.getContent())
                .build();

        return postRepository.save(post);
    }

    @Transactional
    public Comment createComment(CreateCommentDTO createCommentDTO) {

        // Optional.orElseThrow() 사용하여 존재여부만 확인하고 바로 처리
        User user = userRepository.findById(createCommentDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. ID : " + createCommentDTO.getUserId()));
        Post post = postRepository.findById(createCommentDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID : " + createCommentDTO.getPostId()));

        // 댓글 작성
        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(createCommentDTO.getContent())
                .build();

        return commentRepository.save(comment);
    }

    @Transactional
    public List<PostsResponseDTO> findAllPosts() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(post -> new PostsResponseDTO(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getAuthor(),
                post.getCreatedAt())).collect(Collectors.toList());
    }

    public List<PostsResponseDTO> findPostsByEmail(String email) {
        List<Post> postList = postRepository.findByUserEmail(email);
        return postList.stream().map(post -> new PostsResponseDTO(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getAuthor(),
                post.getCreatedAt())).collect(Collectors.toList());
    }

    public List<CommentsResponseDTO> findAllComments() {
        List<Comment> commentList = commentRepository.findAll();
        return commentList.stream().map(comment -> new CommentsResponseDTO(
                comment.getCommentId(),
                comment.getContent(),
                comment.getUser().getAuthor(),
                comment.getPost().getPostId(),
                comment.getCreatedAt())).collect(Collectors.toList());
    }
    //게시판 수정
    @Transactional
    public void updateBoard(UpdateBoardDto dto) {
        Post post = postRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException("해당 게시물을 찾을 수 없습니다. Board ID: " + dto.getBoardId()));

        if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
            post.setTitle(dto.getTitle());
        }
        if (dto.getContent() != null && !dto.getContent().isEmpty()) {
            post.setContent(dto.getContent());
        }
        postRepository.save(post);
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
    public void deleteBoard(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 Id로 작성한 게시물을 찾을 수 없습니다.: " + postId));
        postRepository.delete(post);
    }

    //댓글 삭제
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("해당 Id로 작성한 댓글을 찾을 수 없습니다.: " + commentId));
        commentRepository.delete(comment);
    }
}