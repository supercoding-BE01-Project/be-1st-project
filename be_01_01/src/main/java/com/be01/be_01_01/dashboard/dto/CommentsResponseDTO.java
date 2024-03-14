package com.be01.be_01_01.dashboard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentsResponseDTO {
    private Integer commentId;
    private String content;
    private String author;
    private Integer postId;
    private LocalDateTime createdAt;
}
