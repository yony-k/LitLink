package com.yonyk.litlink.global.security.service;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.yonyk.litlink.domain.member.entity.Member;
import com.yonyk.litlink.domain.member.service.MemberService;
import com.yonyk.litlink.global.security.details.OAuth2UserDTO;
import com.yonyk.litlink.global.security.details.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final MemberService memberService;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    log.info("체크0");

    // OAuth2 제공자로부터 사용자 정보 얻어오기
    Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();

    log.info("체크1");

    // OAuth2 제공자 타입 가져오기
    String registrationId = userRequest.getClientRegistration().getRegistrationId();

    log.info("체크2");

    // 사용자 식별 정보 가져오기
    String userNameAttributeName =
        userRequest
            .getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();

    log.info("체크3" + userNameAttributeName);

    // 사용자 정보 1차 변환
    OAuth2UserDTO oAuth2UserDTO =
        OAuth2UserDTO.of(registrationId, userNameAttributeName, oAuth2UserAttributes);

    log.info("체크4" + oAuth2UserDTO);

    // 등록된 회원인지 확인 후 회원가입 or 등록된 Member 객체 가져오기
    Member member = memberService.getOrRegister(oAuth2UserDTO);

    log.info("체크5" + member);

    // 시큐리티 세션에 등록
    return new PrincipalDetails(member, oAuth2UserAttributes);
  }
}
