package com.be01.be_01_01.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BoardResponseDTO {
    private Integer boardId;
    private String title;
    private String content;
    private String name;
    private LocalDateTime createdAt;
}
