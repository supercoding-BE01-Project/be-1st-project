package com.be01.be_01_01.dashboard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseDTO {
    private Integer boardId;
    private String title;
    private String content;
    private String name;
    private LocalDateTime createdAt;
}
