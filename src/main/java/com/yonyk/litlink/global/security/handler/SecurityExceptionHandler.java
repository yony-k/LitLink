package com.yonyk.litlink.global.security.handler;
import com.yonyk.litlink.global.error.exceptionType.SecurityExceptionType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class SecurityExceptionHandler {
  // 예외 종류 확인해서 적절한 메세지 리턴
  public String getExceptionMessage(Exception e) {
    String exceptioin = e.getClass().getSimpleName();
    String message = "";

    switch (exceptioin) {
      case "JsonParseException":
        message = SecurityExceptionType.INVALID_JSON_REQUEST.getMessage();
        break;
      case "JsonMappingException":
        message = SecurityExceptionType.INVALID_JSON_REQUEST.getMessage();
        break;
      case "IOException":
        message = SecurityExceptionType.IO_ERROR_PROCESSING_REQUEST.getMessage();
        break;
      case "AuthenticationException":
        message = SecurityExceptionType.INVALID_CREDENTIALS.getMessage();
        break;
      case "MalformedJwtException":
        message = SecurityExceptionType.INVALID_JWT_TOKEN.getMessage();
        break;
      case "ExpiredJwtException":
        message = SecurityExceptionType.EXPIRED_JWT_TOKEN.getMessage();
        break;
      case "SignatureException":
        message = SecurityExceptionType.INVALID_JWT_SIGNATURE.getMessage();
        break;
      case "UnsupportedJwtException":
        message = SecurityExceptionType.UNSUPPORTED_JWT_TOKEN.getMessage();
        break;
      case "IllegalArgumentException":
        message = SecurityExceptionType.EMPTY_JWT_CLAIMS.getMessage();
        break;
      case "JwtException":
        message = SecurityExceptionType.JWT_PROCESSING_ERROR.getMessage();
        break;
      case "SecurityException":
        message = SecurityExceptionType.GENERAL_SECURITY_ERROR.getMessage();
        break;
      default:
        message = SecurityExceptionType.SERVER_ERROR.getMessage();
    }

    return message;
  }

  // 클라이언트에게 응답 보내기
  public void sendResponse(String message, HttpStatus httpStatus, HttpServletResponse response)
          throws IOException {
    response.setContentType(MediaType.TEXT_PLAIN_VALUE);
    response.setCharacterEncoding("UTF-8");
    response.setStatus(httpStatus.value());
    response.getWriter().write(message);
    response.getWriter().flush();
  }
}
