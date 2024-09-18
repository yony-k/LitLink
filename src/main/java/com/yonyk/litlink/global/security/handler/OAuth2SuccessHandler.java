package com.yonyk.litlink.global.security.handler;

import com.yonyk.litlink.global.security.details.PrincipalDetails;
import com.yonyk.litlink.global.security.dto.JwtDTO;
import com.yonyk.litlink.global.security.redis.RefreshToken;
import com.yonyk.litlink.global.security.redis.RefreshTokenRepository;
import com.yonyk.litlink.global.security.util.CookieProvider;
import com.yonyk.litlink.global.security.util.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

  private final JwtProvider jwtProvider;
  private final CookieProvider cookieProvider;
  private final RefreshTokenRepository refreshTokenRepository;
  private final SecurityResponseHandler securityResponseHandler;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    // accessToken, refreshToken 생성
    JwtDTO jwtDTO = jwtProvider.getLoginToken(authentication);
    // 각 토큰 저장
    addTokensToResponse(response, jwtDTO);
    // Redis에 refreshToken 저장
    storeRefreshToken(authentication, jwtDTO);
    securityResponseHandler.sendResponse("로그인 성공", HttpStatus.OK, response);
  }

  // accessToken은 헤더에 저장, refreshToken은 쿠키에 저장
  private void addTokensToResponse(HttpServletResponse response, JwtDTO jwtDTO) {
    response.setHeader(jwtProvider.accessTokenHeader, jwtProvider.prefix + jwtDTO.accessToken());
    response.addCookie(cookieProvider.createRefreshTokenCookie(jwtDTO.refreshToken()));
  }

  // redis에 refreshToken 저장, memberId는 String으로 변환 후 저장
  private void storeRefreshToken(Authentication authResult, JwtDTO jwtDTO) {
    long memberId = ((PrincipalDetails) authResult.getPrincipal()).getMember().getMemberId();
    refreshTokenRepository.save(
            new RefreshToken(jwtDTO.refreshToken(), String.valueOf(memberId)));
  }
}
