package com.be01.be_01_01.dashboard.web.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class DashboardPostsDTO {
    @NonNull
    private String title;
    @NonNull
    private String content;
    private String name;
}
