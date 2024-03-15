package com.be01.be_01_01.dashboard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostsResponseDTO {
    private Integer postId;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
}
