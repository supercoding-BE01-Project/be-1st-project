package com.be01.be_01_01.dashboard.web.dto;

import com.be01.be_01_01.dashboard.repository.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class UserLoginDTO {

    private String email;
    private String password;

    public UserLoginDTO(User user){
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
