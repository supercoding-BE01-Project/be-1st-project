package com.be01.be_01_01.dashboard.web.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private int status;
    private String message;
    private String jwtToken;
}
