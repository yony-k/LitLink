package com.yonyk.litlink.domain.bookmark.controller;

import com.yonyk.litlink.global.security.details.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookMarkController {

  // 북마크 공유 기능
  @PostMapping
  public RequestEntity<String> saveBookMark(@AuthenticationPrincipal PrincipalDetails principalDetails) {




    return null;
  }

}
