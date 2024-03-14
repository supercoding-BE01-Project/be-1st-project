package com.be01.be_01_01.dashboard.dto;

import lombok.*;

@Getter
@Setter
public class CreateCommentDTO {
    private Integer userId;
    private Integer postId;
    private String content;
}
