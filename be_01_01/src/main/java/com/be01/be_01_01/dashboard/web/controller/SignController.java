package com.be01.be_01_01.dashboard.web.controller;

import com.be01.be_01_01.dashboard.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/sign")
public class SignController {

    private final AuthService authService;

}
