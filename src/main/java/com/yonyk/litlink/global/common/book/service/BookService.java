package com.yonyk.litlink.global.common.book.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yonyk.litlink.global.common.book.entity.Book;
import com.yonyk.litlink.global.common.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {
  private final BookAPIService bookAPIService;
  private final BookRepository bookRepository;

  // 책 정보 가져오기 혹은 저장 후 가져오기
  public Book getOrSaveBook(String isbn) {
    Book book = null;
    // DB에 저장된 책이 있는지 확인
    Optional<Book> findBook = bookRepository.findByIsbn(isbn);

    // 존재 여부에 따라 Book 엔티티 생성
    if (findBook.isPresent()) book = findBook.get();
    else {
      book = bookAPIService.serchBook(isbn).toBook();
      book = bookRepository.save(book);
    }
    return book;
  }

  // 책 정보 저장 및 업데이트
  public void incrementLikeCount(Book book) {
    book.incrementLikeCount();
    bookRepository.save(book);
  }
}
