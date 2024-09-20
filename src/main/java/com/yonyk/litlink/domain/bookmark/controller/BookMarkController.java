package com.yonyk.litlink.domain.bookmark.controller;

import com.yonyk.litlink.domain.bookmark.dto.response.BookMarkDTO;
import com.yonyk.litlink.domain.bookmark.service.BookMarkService;
import com.yonyk.litlink.global.security.details.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookMarkController {

  private final BookMarkService bookMarkService;

  // 북마크 공유 기능
  @PostMapping
  public ResponseEntity<String> saveBookMark(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                             @RequestParam("bookMarkId") long bookMarkId) {
    return ResponseEntity.ok(bookMarkService.shareBookMark(principalDetails.getMember().getMemberId(), bookMarkId));
  }

  // 북마크 공유 기능으로 북마크 상세 조회
  @GetMapping
  public ResponseEntity<BookMarkDTO> getBookMark(@RequestParam("share") String share) {
    return ResponseEntity.ok(bookMarkService.getShareBookMark(share));
  }
}
