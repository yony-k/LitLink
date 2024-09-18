package com.yonyk.litlink.domain.member.controller;

import com.yonyk.litlink.domain.member.entity.Member;
import com.yonyk.litlink.domain.member.service.MemberService;
import com.yonyk.litlink.global.security.details.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

  private final MemberService memberService;

  @DeleteMapping("/")
  public ResponseEntity<String> test(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    String member = principalDetails.getMember().toString();
    return ResponseEntity.ok(principalDetails.getMember().toString());
  }
}
