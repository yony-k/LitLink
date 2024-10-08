package com.yonyk.litlink.global.security.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.yonyk.litlink.global.security.util.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // 리퀘스트에서 accessToken 가져오기
    String accessToken = request.getHeader(jwtProvider.accessTokenHeader);

    // accessToken이 없는 경우 필터 통과
    if (accessToken == null) {
      filterChain.doFilter(request, response);
    } else {
      // accessToken 형식 검사
      if (StringUtils.hasText(accessToken) || accessToken.startsWith("Bearer ")) {
        try {
          // accessToken을 사용해서 인증객체 등록
          SecurityContextHolder.getContext()
              .setAuthentication(jwtProvider.getAuthentication(accessToken));
        } catch (Exception e) {
          request.setAttribute("exception", e);
        }
      }
      filterChain.doFilter(request, response);
    }
  }
}
