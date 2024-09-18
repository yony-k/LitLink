package com.yonyk.litlink.global.security.handler;

import com.yonyk.litlink.global.error.exceptionType.SecurityExceptionType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private final SecurityExceptionHandler securityExceptionHandler;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    log.error("권한 검증 오류 발생");
    securityExceptionHandler.sendResponse(
            SecurityExceptionType.UNAUTHRIZED_REQUEST.getMessage(), HttpStatus.FORBIDDEN, response);
  }
}
