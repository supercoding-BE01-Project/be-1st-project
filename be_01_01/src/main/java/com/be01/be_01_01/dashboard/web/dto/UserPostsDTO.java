package com.be01.be_01_01.dashboard.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPostsDTO {

    private String userId;
    private String email;
    private String phoneNum;

}
