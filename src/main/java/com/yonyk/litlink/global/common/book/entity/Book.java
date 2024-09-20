package com.yonyk.litlink.global.common.book.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
@Table(name = "book")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long bookId;

  private String title;
  private String link;
  private String image;
  private String author;
  private int discount;
  private String publisher;
  private String isbn;

  @Column(columnDefinition = "TEXT")
  private String description;

  private LocalDate pubdate;
  private int likeCount;

  public void incrementLikeCount() {
    likeCount++;
  }

  public void decrementLikeCount() {
    if (likeCount > 0) likeCount--;
  }
}
