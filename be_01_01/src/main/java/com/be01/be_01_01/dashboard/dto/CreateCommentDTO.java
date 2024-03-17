package com.be01.be_01_01.dashboard.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentDTO {
    private String content;
    // userId를 사용해서 작성자를 검증하기 위해 author 대신 userId 사용
    private Integer userId;
    private Integer postId;
}
