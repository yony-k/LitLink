package com.yonyk.litlink.global.common.book.repository;

import com.yonyk.litlink.global.common.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
  Optional<Book> findByIsbn(String isbn);
}
