package com.yonyk.litlink.domain.book.controller;

import com.yonyk.litlink.global.common.dto.request.BookSerchDTO;
import com.yonyk.litlink.global.common.dto.response.BookDTO;
import com.yonyk.litlink.global.common.service.BookAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

  private final BookAPIService bookService;

  // 책 검색
  @GetMapping
  public ResponseEntity<List<BookDTO>> book(@RequestBody BookSerchDTO bookSerchDTO) {
    return ResponseEntity.ok(bookService.serchBooks(bookSerchDTO));
  }
}
