package com.be01.be_01_01.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostsResponseDTO {
    private Integer postId;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
}
