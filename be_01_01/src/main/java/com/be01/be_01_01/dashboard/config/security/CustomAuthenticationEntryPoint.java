package com.be01.be_01_01.dashboard.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        // commence 메소드는 인증 과정에서 예외가 발생했을 때 실행됩니다.
        // HttpServletRequest와 HttpServletResponse 객체를 매개변수로 받으며, AuthenticationException 객체를 통해 발생한 예외 정보를 얻을 수 있습니다.
        response.sendRedirect("/exceptions/entrypoint");
        // 사용자를 "/exceptions/entrypoint" 경로로 리다이렉트 합니다.
        // 이는 사용자가 인증되지 않았을 때 보여줄 페이지 또는 로그인 페이지로 이동시키는 용도로 사용될 수 있습니다.
    }
    //이 코드는 스프링 시큐리티에서 인증 과정에서 예외가 발생하거나 인증이 필요한 자원에 접근했을 때 사용자를
    // 특정 페이지로 리다이렉트하는 커스텀 AuthenticationEntryPoint 구현 예시입니다.
    // 인증 예외가 발생하면, 설정된 URL(/exceptions/entrypoint)로 사용자를 안내하여 더 나은 사용자 경험을 제공할 수 있습니다.





}
