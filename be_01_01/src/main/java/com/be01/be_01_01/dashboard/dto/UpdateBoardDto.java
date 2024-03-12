package com.be01.be_01_01.dashboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateBoardDto {
    private Integer boardId;

    private String title;
    private String content;
}