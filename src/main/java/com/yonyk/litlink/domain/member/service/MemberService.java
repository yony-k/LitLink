package com.yonyk.litlink.domain.member.service;

import com.yonyk.litlink.domain.member.entity.Member;
import com.yonyk.litlink.domain.member.repository.MemberRepository;
import com.yonyk.litlink.global.security.details.OAuth2UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberRepository memberRepository;

  public Member getOrRegister(OAuth2UserDTO oAuth2UserDTO) {
    // 등록된 회원 있는지 확인
    Optional<Member> member = memberRepository.findByAuthIdAndProvider(oAuth2UserDTO.authId(), oAuth2UserDTO.provider());
    // 존재한다면 DB의 Member 반환
    if (member.isPresent()) {
      return member.get();
    }
    // 존재하지 않는다면 회원가입 후 Member 반환
    return memberRepository.save(oAuth2UserDTO.toEntity());
  }

}
