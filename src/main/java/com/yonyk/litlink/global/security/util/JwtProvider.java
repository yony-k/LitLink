package com.yonyk.litlink.global.security.util;

import com.yonyk.litlink.domain.member.entity.Member;
import com.yonyk.litlink.domain.member.repository.MemberRepository;
import com.yonyk.litlink.global.error.exceptionType.SecurityExceptionType;
import com.yonyk.litlink.global.security.details.PrincipalDetails;
import com.yonyk.litlink.global.security.dto.JwtDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

  private final MemberRepository memberRepository;

  // jwt 시크릿 키
  @Value("${jwt.secret}")
  String secretKey;

  // jwt 파싱 암호키
  private SecretKey key;

  // 객체 생성 후 초기화
  @PostConstruct
  public void init() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  // 액세스 토큰이 저장될 헤더 필드 이름
  @Value("${jwt.access-token-header}")
  public String accessTokenHeader;

  // 액세스 토큰 앞에 지정될 프리픽스
  @Value("${jwt.prefix}")
  public String prefix;

  // 액세스 토큰 만료 기한
  @Value("${jwt.access-token-TTL}")
  private int accessTokenTTL;

  // 리프레시 토큰 만료 기한
  @Value("${jwt.refresh-token-TTL}")
  private int refreshTokenTTL;

  // 로그인 성공 시 액세스 토큰, 리프레시 토큰 발급
  public JwtDTO getLoginToken(Authentication authResult) {
    PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
    String authorities =
            principalDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("권한이 없는 사용자"));
    long memberId = principalDetails.getMember().getMemberId();
    return generateJwt(authorities, memberId);
  }

  // 액세스 토큰, 리프레시 토큰이 담긴 JwtRecord 생성 메소드
  public JwtDTO generateJwt(String authorities, long memberId) {
    long now = (new Date()).getTime();

    // 액세스 토큰 생성
    String accessToken =
            Jwts.builder()
                    .subject(String.valueOf(memberId))
                    .claim("authorities", authorities)
                    .expiration(new Date(now + (accessTokenTTL * 1000L)))
                    .signWith(key)
                    .compact();
    // 리프레시 토큰 생성
    String refreshToken =
            Jwts.builder()
                    .subject(String.valueOf(memberId))
                    .expiration(new Date(now + (refreshTokenTTL * 1000L)))
                    .signWith(key)
                    .compact();

    return new JwtDTO(accessToken, refreshToken);
  }
  
  // accessToken 파싱 후 인증객체 생성
  public Authentication getAuthentication(String token) {
    // accessToken에서 prefix 제거
    String accessToken = token.substring(prefix.length());
    // 액세스 토큰 해석하여 정보 가져오기
    Claims claims =
            Jwts.parser().verifyWith(key).build().parseSignedClaims(accessToken).getPayload();
    // accessToken에 담긴 memberId로 Member 가져오기
    Member findMember = memberRepository.findById(Long.parseLong(claims.getSubject()))
            .orElseThrow(() -> new UsernameNotFoundException(SecurityExceptionType.MEMBER_NOT_FOUND.getMessage()));
    // PrincipalDetails 생성
    UserDetails userDetails = PrincipalDetails.builder().member(findMember).build();
    // 인증객체 생성
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }
}
