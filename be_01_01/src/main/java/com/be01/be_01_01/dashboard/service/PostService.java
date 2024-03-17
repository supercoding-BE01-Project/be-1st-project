package com.be01.be_01_01.dashboard.service;


import com.be01.be_01_01.dashboard.dto.*;
import com.be01.be_01_01.dashboard.repository.Comment.Comment;
import com.be01.be_01_01.dashboard.repository.Post.Post;
import com.be01.be_01_01.dashboard.repository.Post.PostJpaRepository;
import com.be01.be_01_01.dashboard.repository.Comment.CommentJpaRepository;
import com.be01.be_01_01.dashboard.repository.User.User;
import com.be01.be_01_01.dashboard.repository.User.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostJpaRepository postRepository;
    private final UserJpaRepository userJpaRepository;
    private final CommentJpaRepository commentJpaRepository;

    @Transactional
    public Post createPost(CreatePostDTO createPostDTO) {

        // Optional.orElseThrow() 사용하여 존재여부만 확인하고 바로 처리
        User user = userJpaRepository.findById(createPostDTO.getUserId())
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
        User user = userJpaRepository.findById(createCommentDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. ID : " + createCommentDTO.getUserId()));
        Post post = postRepository.findById(createCommentDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID : " + createCommentDTO.getPostId()));

        // 댓글 작성
        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(createCommentDTO.getContent())
                .build();

        return commentJpaRepository.save(comment);
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
        List<Comment> commentList = commentJpaRepository.findAll();
        return commentList.stream().map(comment -> new CommentsResponseDTO(
                comment.getCommentId(),
                comment.getContent(),
                comment.getUser().getAuthor(),
                comment.getPost().getPostId(),
                comment.getCreatedAt())).collect(Collectors.toList());
    }
    //게시판 수정
    @Transactional
    public void updatePost(Integer postId, UpdatePostDTO updatePostDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시물을 찾을 수 없습니다. Post ID: " + postId));

        if (updatePostDTO.getTitle() != null && !updatePostDTO.getTitle().isEmpty()) {
            post.setTitle(updatePostDTO.getTitle());
        }
        if (updatePostDTO.getContent() != null && !updatePostDTO.getContent().isEmpty()) {
            post.setContent(updatePostDTO.getContent());
        }
        postRepository.save(post);
    }

    //댓글 수정
    @Transactional
    public void updateComment(Integer commentId, UpdateCommentDTO updateCommentDTO) {
        Comment comment = commentJpaRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다. Comment ID: " + commentId));

        if (updateCommentDTO.getContent() != null && !updateCommentDTO.getContent().isEmpty()) {
            comment.setContent(updateCommentDTO.getContent());
        }
        commentJpaRepository.save(comment);
    }

    //게시판 삭제
    @Transactional
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 Id로 작성한 게시물을 찾을 수 없습니다.: " + postId));
        postRepository.delete(post);
    }

    //댓글 삭제
    public void deleteComment(Integer commentId) {
        Comment comment = commentJpaRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("해당 Id로 작성한 댓글을 찾을 수 없습니다.: " + commentId));
        commentJpaRepository.delete(comment);
    }
}