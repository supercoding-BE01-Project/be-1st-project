package com.be01.be_01_01.dashboard.dto;

import com.be01.be_01_01.dashboard.repository.User.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserPostsDTO {

    private Integer userId;
    private String email;

    public UserPostsDTO(User user){
        this.userId = user.getUserId();
        this.email = user.getEmail();
    }

}
