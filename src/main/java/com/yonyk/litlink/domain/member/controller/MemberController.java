package com.yonyk.litlink.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.yonyk.litlink.domain.member.service.MemberService;
import com.yonyk.litlink.global.security.details.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

  private final MemberService memberService;

  // 회원탈퇴
  @DeleteMapping
  public ResponseEntity<String> cancleAccount(
      @AuthenticationPrincipal PrincipalDetails principalDetails) {
    memberService.cancleAccount(principalDetails.getMember().getMemberId());
    return ResponseEntity.ok("회원탈퇴가 성공적으로 완료되었습니다.");
  }

  // refreshToken 재발급
  @PostMapping("/refresh-token")
  public ResponseEntity<String> reissueRefreshToken(
      HttpServletRequest request, HttpServletResponse response) {
    memberService.reissueRefreshToken(request, response);
    return ResponseEntity.ok("refreshToken 재발급이 성공적으로 완료되었습니다.");
  }
}
