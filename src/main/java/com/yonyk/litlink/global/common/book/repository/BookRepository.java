package com.yonyk.litlink.global.common.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yonyk.litlink.global.common.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

  // 북마크 저장 시 DB에 저장된 책 정보 가져오기
  Optional<Book> findByIsbn(String isbn);
}
