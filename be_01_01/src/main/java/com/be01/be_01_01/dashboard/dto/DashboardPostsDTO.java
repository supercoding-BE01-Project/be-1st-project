package com.be01.be_01_01.dashboard.dto;

import lombok.*;

@Getter
@Setter
public class DashboardPostsDTO {
    private Integer userId;
    private Integer boardId;
    private String title;
    private String content;
}
