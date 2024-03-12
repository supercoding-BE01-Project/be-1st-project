package com.be01.be_01_01.dashboard.web.dto;

import com.be01.be_01_01.dashboard.repository.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserLoginDTO {

    private String email;
    private String password;

    public UserLoginDTO(User user){
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
