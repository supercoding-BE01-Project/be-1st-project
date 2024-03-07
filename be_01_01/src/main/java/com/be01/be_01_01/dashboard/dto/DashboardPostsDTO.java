package com.be01.be_01_01.dashboard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardPostsDTO {
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private String userId;
}
