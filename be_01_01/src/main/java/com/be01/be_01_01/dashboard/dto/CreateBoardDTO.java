package com.be01.be_01_01.dashboard.dto;

import lombok.*;

@Getter
@Setter
public class CreateBoardDTO {
    private Integer userId;
    private String title;
    private String content;
}
