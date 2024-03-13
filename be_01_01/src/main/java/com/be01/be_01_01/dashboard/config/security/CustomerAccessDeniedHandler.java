package com.be01.be_01_01.dashboard.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomerAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // AccessDeniedHandler 인터페이스를 구현합니다.
        // 이 인터페이스는 사용자가 접근 권한이 없는 자원에 접근했을 때 실행되는 메소드를 정의합니다.

        // 접근 거부 예외의 스택 트레이스를 콘솔에 출력합니다.
        // 실제 운영 환경에서는 이 방법 대신 로그를 기록하는 것이 더 적합할 수 있습니다.
        accessDeniedException.printStackTrace();

        // 사용자를 "/exceptions/access-denied" 경로로 리다이렉트합니다.
        // 이는 사용자가 접근 권한이 없을 때 보여줄 페이지로 이동시키는 용도로 사용될 수 있습니다.
        response.sendRedirect("/exceptions/access-denied");
    }
    //이 코드는 스프링 시큐리티에서 사용자가 접근 권한이 없는 자원에 접근했을 때, 특정 행동을 취하도록 하는
    // AccessDeniedHandler 인터페이스의 커스텀 구현체입니다.
    // 여기에는 접근 거부 예외가 발생했을 때의 로깅과 사용자를 접근 거부 페이지로 리다이렉트하는 기능이 포함되어 있습니다.


}
