package com.yonyk.litlink.domain.bookmark.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyk.litlink.domain.bookmark.entity.BookMark;
import com.yonyk.litlink.domain.bookmark.repository.BookMarkRepository;
import com.yonyk.litlink.global.common.book.service.BookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookMarkLikeService {

  private final BookMarkRepository bookMarkRepository;
  private final BookService bookService;

  // 북마크 좋아요 표시
  @Transactional
  public void likeBookMark(BookMark bookMark) {
    // liked 필드를 true로 값을 바꾸고 저장
    bookMark.like();
    bookMarkRepository.save(bookMark);
    // Book 엔티티의 likeCount 변경
    bookService.incrementLikeCount(bookMark.getBook());
  }
}
