package com.yonyk.litlink.domain.bookmark.entity;

import jakarta.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.yonyk.litlink.domain.member.entity.Member;
import com.yonyk.litlink.global.common.BaseEntity;
import com.yonyk.litlink.global.common.book.entity.Book;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@SQLDelete(sql = "UPDATE bookmark SET deleted_at = CURRENT_TIMESTAMP WHERE bookmark_id = ?")
@SQLRestriction("deleted_at is NULL")
@Entity
@Table(name = "bookmark")
public class BookMark extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long bookmarkId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id")
  private Book book;

  @Builder.Default private boolean liked = false;

  public void like() {
    this.liked = true;
  }

  public void unlike() {
    this.liked = false;
  }
}
