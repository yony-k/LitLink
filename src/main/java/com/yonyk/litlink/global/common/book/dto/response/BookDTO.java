package com.yonyk.litlink.global.common.book.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yonyk.litlink.global.common.book.entity.Book;

public record BookDTO(
    String title,
    String link,
    String image,
    String author,
    int discount,
    String publisher,
    String isbn,
    String description,
    @JsonFormat(pattern = "yyyyMMdd") LocalDate pubdate) {
  public Book toBook() {
    return Book.builder()
        .title(title)
        .link(link)
        .image(image)
        .author(author)
        .discount(discount)
        .publisher(publisher)
        .isbn(isbn)
        .description(description)
        .pubdate(pubdate)
        .build();
  }
}
