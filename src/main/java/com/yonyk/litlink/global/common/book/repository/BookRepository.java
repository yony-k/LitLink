package com.yonyk.litlink.global.common.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yonyk.litlink.global.common.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
  Optional<Book> findByIsbn(String isbn);
}
