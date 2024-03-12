package com.be01.be_01_01.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDTO {
    private Integer commentId;
    private String content;
    private String name;
    private Integer boardId;
    private LocalDateTime createdAt;
}
