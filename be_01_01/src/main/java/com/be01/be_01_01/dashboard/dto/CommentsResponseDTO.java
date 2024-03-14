package com.be01.be_01_01.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentsResponseDTO {
    private Integer commentId;
    private String content;
    private String author;
    private Integer postId;
    private LocalDateTime createdAt;
}
