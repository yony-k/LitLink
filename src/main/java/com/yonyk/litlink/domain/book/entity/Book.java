package com.yonyk.litlink.domain.book.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
@Table(name = "book")
public class Book {
  @Id
  private long bookId;
  private String title;
  private String link;
  private String image;
  private String author;
  private int discount;
  private String publisher;
  private String isbn;
  private String description;
  private LocalDateTime pubdate;
  private int likeCount;
}
