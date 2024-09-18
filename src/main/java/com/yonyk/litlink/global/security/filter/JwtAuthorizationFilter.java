package com.yonyk.litlink.global.security.filter;

import com.yonyk.litlink.global.security.util.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String accessToken = request.getHeader(jwtProvider.accessTokenHeader);

    if(accessToken == null) {
      filterChain.doFilter(request, response);
    } else {
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
