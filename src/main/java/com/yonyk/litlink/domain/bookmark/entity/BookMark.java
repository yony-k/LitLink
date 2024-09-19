package com.yonyk.litlink.domain.bookmark.entity;

import com.yonyk.litlink.global.common.book.entity.Book;
import com.yonyk.litlink.domain.member.entity.Member;
import com.yonyk.litlink.domain.note.entity.Note;
import com.yonyk.litlink.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

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

  @OneToMany(mappedBy = "bookmark", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Note> notes;
}
