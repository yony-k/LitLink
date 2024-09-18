package com.yonyk.litlink.global.security.handler;

import com.yonyk.litlink.global.error.exceptionType.SecurityExceptionType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final SecurityResponseHandler securityResponseHandler;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    // 예외 종류 확인
    Exception e = (Exception) request.getAttribute("exception");
    String message = "";

    // 예외 종류에 따른 메세지 생성
    if (e == null) {
      message = SecurityExceptionType.REQUIRED_AUTHENTICATION.getMessage();
    } else {
      message = securityResponseHandler.getExceptionMessage(e);
    }

    // 클라이언트에게 응답
    securityResponseHandler.sendResponse(message, HttpStatus.UNAUTHORIZED, response);
  }
}
