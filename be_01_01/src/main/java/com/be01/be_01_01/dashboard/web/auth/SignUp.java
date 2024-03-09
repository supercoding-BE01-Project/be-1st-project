package com.be01.be_01_01.dashboard.web.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUp {
    private String name;
    private String email;
    private String password;
    private String phoneNum;
}
