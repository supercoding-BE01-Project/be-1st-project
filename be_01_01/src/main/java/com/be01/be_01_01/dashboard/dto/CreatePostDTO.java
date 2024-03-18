package com.be01.be_01_01.dashboard.dto;

import lombok.*;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDTO {
    private String title;
    private String content;
    private String author;
    private String email;
}
