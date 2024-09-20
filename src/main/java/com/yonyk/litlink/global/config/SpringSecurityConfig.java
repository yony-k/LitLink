package com.yonyk.litlink.global.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.yonyk.litlink.global.security.filter.JwtAuthorizationFilter;
import com.yonyk.litlink.global.security.handler.CustomAccessDeniedHandler;
import com.yonyk.litlink.global.security.handler.CustomAuthenticationEntryPoint;
import com.yonyk.litlink.global.security.handler.CustomLogoutHandler;
import com.yonyk.litlink.global.security.handler.OAuth2SuccessHandler;
import com.yonyk.litlink.global.security.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

  // accessToken 헤더 이름
  @Value("${jwt.access-token-header}")
  public String accessTokenHeader;

  // OAuth2 로그인 처리 서비스
  private final CustomOAuth2UserService customOAuth2UserService;
  // OAuth2 로그인 성공 처리 핸들러
  private final OAuth2SuccessHandler customOAuth2SuccessHandler;
  // 권한 예외 처리 핸들러
  private final CustomAccessDeniedHandler customAccessDeniedHandler;
  // 로그아웃 핸들러
  private final CustomLogoutHandler customLogoutHandler;
  // 인가 필터
  private final JwtAuthorizationFilter jwtAuthorizationFilter;
  // 인가 예외 처리 핸들러
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

  // 특정 요청 경로에 대해서는 시큐리티 검사 무시
  // resources는 이미지, css, javascript 파일등에 대한 요청을 의미함
  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    return (webSecurity) -> webSecurity.ignoring().requestMatchers("/resources/**", "/favicon.ico");
  }

  // 지정된 출처(주소)에서 오는 요청 관련 설정
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    return request -> {
      CorsConfiguration config = new CorsConfiguration();
      // 서버에서 내려보내는 헤더의 특정 내용을 볼 수 있도록 허용
      config.addExposedHeader(accessTokenHeader);
      // 클라이언트에서 보내오는 헤더와 메서드 허용
      config.setAllowedHeaders(Collections.singletonList("*"));
      config.setAllowedMethods(Collections.singletonList("*"));
      // 모든 출처에서 오는 요청 허용
      config.setAllowedOrigins(Collections.singletonList("*"));
      // 쿠키 등 자격증명이 포함된 요청 허용
      config.setAllowCredentials(true);
      return config;
    };
  }

  // 필터체인 설정
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // 사용자가 의도하지 않은 요청 방지 설정
        .csrf(csrfConf -> csrfConf.disable())
        // 위에서 적은 Cors 설정 적용
        .cors(c -> c.configurationSource(corsConfigurationSource()))
        // 기본 폼 로그인 기능 비활성화
        .formLogin(loginConf -> loginConf.disable())
        // 특정 경로에 대한 인가 설정
        .authorizeHttpRequests(
            auth ->
                auth
                    // Swagger 설정
                    .requestMatchers("/v3/**", "/swagger-ui/**")
                    .permitAll()
                    // 회원가입, 로그인, 액세스 토큰 재발급
                    .requestMatchers(
                        "/oauth2/**", "/api/book", "/graphiql", "/api/members/refresh-token")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/bookmark/**", "/api/note/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/bookmark/**")
                    .authenticated()
                    .requestMatchers("/api/members/**", "/graphql")
                    .authenticated()
                    // 이외 모든 요청 인증 필요
                    .anyRequest()
                    .authenticated())
        .oauth2Login(
            oAuth2 ->
                oAuth2
                    .userInfoEndpoint(c -> c.userService(customOAuth2UserService))
                    .successHandler(customOAuth2SuccessHandler))

        // 예외 처리 핸들러 설정
        .exceptionHandling(
            exceptionHandling ->
                exceptionHandling
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler))
        // 로그아웃 처리
        .logout(logout -> logout.logoutUrl("/api/logout").addLogoutHandler(customLogoutHandler));
    // 커스텀 필터 설정
    http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
