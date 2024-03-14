package com.be01.be_01_01.dashboard.service;

import com.be01.be_01_01.dashboard.dto.PostsResponseDTO;
import com.be01.be_01_01.dashboard.dto.CommentsResponseDTO;
import com.be01.be_01_01.dashboard.dto.CreatePostDTO;
import com.be01.be_01_01.dashboard.dto.CreateCommentDTO;
import com.be01.be_01_01.dashboard.entity.*;
import com.be01.be_01_01.dashboard.repository.PostRepository;
import com.be01.be_01_01.dashboard.repository.CommentRepository;
import com.be01.be_01_01.dashboard.repository.UserRepository;
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
}
