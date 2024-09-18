package com.yonyk.litlink.global.security.util;

import com.yonyk.litlink.global.error.CustomException;
import com.yonyk.litlink.global.error.exceptionType.SecurityExceptionType;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
public class CookieProvider {
  // 리프레시 토큰 쿠키의 이름
  @Value("${cookie.refresh-token.cookie-name}")
  String cookieName;

  // 리프레시 토큰 쿠키의 만료 시간
  @Value("${cookie.refresh-token.limit-time}")
  int cookieLimitTime;

  // 리프레시 토큰 쿠키를 이용해 요청할 수 있는 주소
  @Value("${cookie.refresh-token.domain}")
  String cookieDomain;

  // 리프레시 토큰 쿠키가 사용되는 프로토콜
  @Value("${cookie.refresh-token.http-only}")
  boolean cookieHttpOnly;

  // 리프레시 토큰 쿠키 생성
  public Cookie createRefreshTokenCookie(String refreshToken) {
    Cookie cookie = new Cookie(cookieName, refreshToken);
    cookie.setPath("/");
    cookie.setMaxAge(cookieLimitTime);
    cookie.setDomain(cookieDomain);
    cookie.setHttpOnly(cookieHttpOnly);
    return cookie;
  }

  // 로그아웃 시 리프레시 토큰 삭제
  public Cookie deleteRefreshTokenCookie(HttpServletRequest request) {
    // 리퀘스트에서 refreshToken 빼오기
    Optional<Cookie> findCookie = getRefreshTokenCookie(request);
    // 토큰 만료기간 0으로 설정
    if (findCookie.isPresent()) {
      Cookie cookie = findCookie.get();
      cookie.setMaxAge(0);
      return cookie;
    } else {
      throw new CustomException(SecurityExceptionType.COOKIE_NOT_FOUND);
    }
  }

  // 리퀘스트에서 리프레시 토큰 빼오기
  public Optional<Cookie> getRefreshTokenCookie(HttpServletRequest request) {
    return Arrays.stream(request.getCookies())
            .filter(cookie -> cookieName.equals(cookie.getName()))
            .findFirst();
  }
}
