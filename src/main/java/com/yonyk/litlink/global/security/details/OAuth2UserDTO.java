package com.yonyk.litlink.global.security.details;

import java.util.Map;

import com.yonyk.litlink.domain.member.entity.Member;
import com.yonyk.litlink.domain.member.entity.enums.MemberRole;
import com.yonyk.litlink.global.error.CustomException;
import com.yonyk.litlink.global.error.exceptionType.SecurityExceptionType;

import lombok.Builder;

@Builder
public record OAuth2UserDTO(
    String authId, String memberName, String email, String imageUrl, String provider) {
  // OAuth2 제공자에 따라 OAuth2UserDTO 생성
  public static OAuth2UserDTO of(
      String registrationId,
      String userNameAttributeName,
      Map<String, Object> oAuth2UserAttributes) {

    return switch (registrationId) {
      case "kakao" -> ofKakao(userNameAttributeName, oAuth2UserAttributes);
      default -> throw new CustomException(SecurityExceptionType.ILLEGAL_REGISTRATION_ID);
    };
  }

  // 카카오일 경우
  private static OAuth2UserDTO ofKakao(
      String userNameAttributeName, Map<String, Object> oAuth2UserAttributes) {
    Map<String, Object> account = (Map<String, Object>) oAuth2UserAttributes.get("kakao_account");
    Map<String, Object> profile = (Map<String, Object>) account.get("profile");
    return OAuth2UserDTO.builder()
        .authId(oAuth2UserAttributes.get(userNameAttributeName).toString())
        .memberName(java.lang.String.valueOf(profile.get("nickname")))
        .email(java.lang.String.valueOf(account.get("email")))
        .imageUrl(java.lang.String.valueOf(profile.get("profile_image_url")))
        .provider("KaKao")
        .build();
  }

  // OAuth2UserDTO 를 Member 엔티티로 변환
  public Member toEntity() {
    return Member.builder()
        .authId(authId)
        .memberName(memberName)
        .email(email)
        .imageUrl(imageUrl)
        .provider(provider)
        .memberRole(MemberRole.USER)
        .build();
  }
}
