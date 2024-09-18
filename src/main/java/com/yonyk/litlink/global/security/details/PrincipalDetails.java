package com.yonyk.litlink.global.security.details;

import com.yonyk.litlink.domain.member.entity.Member;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {

  private Member member;
  private Map<String, Object> attributes;

  // OAuth2 제공자에서 사용되는 사용자 고유ID
  @Override
  public String getName() {
    return member.getAuthId();
  }

  // 사용자 닉네임
  @Override
  public String getUsername() {
    return member.getMemberName();
  }

  // 사용자 이메일
  public String getEmail() {
    return member.getEmail();
  }

  // OAuth2 제공자에서 보낸 사용자 정보
  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  // 사용자 권한
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(member.getMemberRole().getRole()));
  }

  @Override
  public String getPassword() {
    return null;
  }
}
