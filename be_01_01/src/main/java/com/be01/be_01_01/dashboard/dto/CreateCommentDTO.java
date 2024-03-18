package com.be01.be_01_01.dashboard.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentDTO {
    private String content;
    // 단순히 사용자명을 게시글의 메타정보로만 사용
    private String author;
    private Integer postId;
}
