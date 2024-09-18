package com.yonyk.litlink.global.security.handler;

import com.yonyk.litlink.global.error.CustomException;
import com.yonyk.litlink.global.error.exceptionType.SecurityExceptionType;
import com.yonyk.litlink.global.security.redis.RefreshToken;
import com.yonyk.litlink.global.security.redis.RefreshTokenRepository;
import com.yonyk.litlink.global.security.util.CookieProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CustomLogoutHandler implements LogoutHandler {

  private final CookieProvider cookieProvider;
  private final RefreshTokenRepository refreshTokenRepository;
  private final SecurityResponseHandler securityResponseHandler;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    try {
      // 리퀘스트에서 refreshToken 만료기한 0으로 설정
      Cookie findCookie = cookieProvider.deleteRefreshTokenCookie(request);
      // 리퀘스트에 쿠키 추가
      response.addCookie(findCookie);
      // redis에서 refreshToken 삭제
      deleteRefreshTokenInRedis(findCookie);
      
      // 클라이언트에 응답
      securityResponseHandler.sendResponse("로그아웃 성공", HttpStatus.OK, response);
    } catch (Exception e) {
      log.error("로그아웃 오류: {}", e.getMessage());
      try {
        securityResponseHandler.sendResponse(e.getMessage(), HttpStatus.NOT_FOUND, response);
      } catch (IOException ex) {
        log.error("로그아웃 오류: {}", e.getMessage());
      }
    }
  }
  // redis에서 refreshToken 삭제하는 메소드
  private void deleteRefreshTokenInRedis(Cookie findCookie) {
    // redis에서 refreshToken 찾기
    Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(findCookie.getValue());
    if (refreshToken.isPresent()) {
      // redis에서 refreshToken 삭제
      refreshTokenRepository.delete(refreshToken.get());
    } else {
      throw new CustomException(SecurityExceptionType.REFRESHTOKEN_NOT_FOUND);
    }
  }
}
