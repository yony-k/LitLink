package com.yonyk.litlink.domain.note.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yonyk.litlink.domain.bookmark.dto.response.BookMarkDTO;
import com.yonyk.litlink.domain.note.service.NoteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/note")
public class NoteController {

  private final NoteService noteService;

  // 북마크 공유 기능으로 노트 목록 조회
  @GetMapping
  public ResponseEntity<BookMarkDTO> getNotes(@RequestParam("share") String share) {
    // return ResponseEntity.ok(bookMarkService.getShareBookMark(share));
    return null;
  }
}
