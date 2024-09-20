package com.yonyk.litlink.domain.member.service;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yonyk.litlink.domain.member.entity.Member;
import com.yonyk.litlink.domain.member.repository.MemberRepository;
import com.yonyk.litlink.global.error.CustomException;
import com.yonyk.litlink.global.error.exceptionType.SecurityExceptionType;
import com.yonyk.litlink.global.security.details.OAuth2UserDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final JwtService jwtService;

  // OAuth2 회원가입 및 로그인
  public Member getOrRegister(OAuth2UserDTO oAuth2UserDTO) {
    // 등록된 회원 있는지 확인
    Optional<Member> member =
        memberRepository.findByAuthIdAndProvider(oAuth2UserDTO.authId(), oAuth2UserDTO.provider());
    // 존재한다면 DB의 Member 반환
    if (member.isPresent()) {
      return member.get();
    }
    // 존재하지 않는다면 회원가입 후 Member 반환
    return memberRepository.save(oAuth2UserDTO.toEntity());
  }

  // 멤버 가져오기
  public Member findById(Long id) {
    return memberRepository
        .findById(id)
        .orElseThrow(
            () ->
                new UsernameNotFoundException(SecurityExceptionType.MEMBER_NOT_FOUND.getMessage()));
  }

  // 회원탈퇴
  public void cancleAccount(long memberId) {
    // 회원이 존재하는지 확인
    Member findMember = findById(memberId);
    // 회원이 존재하지 않는다면 예외 발생
    if (findMember == null) throw new CustomException(SecurityExceptionType.MEMBER_NOT_FOUND);
    // 회원이 존재하면 소프트 딜리트
    else memberRepository.delete(findMember);
  }

  // refreshToken 재발급
  public void reissueRefreshToken(HttpServletRequest request, HttpServletResponse response) {
    jwtService.reissueRefreshToken(request, response);
  }
}
