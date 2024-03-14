package com.be01.be_01_01.dashboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCommentDto {
    private Integer userId;
    private Integer boardId;
    private Integer commentId;
    private String title;
    private String content;
}
