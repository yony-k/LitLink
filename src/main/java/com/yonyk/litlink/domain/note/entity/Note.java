package com.yonyk.litlink.domain.note.entity;

import com.yonyk.litlink.domain.bookmark.entity.BookMark;
import com.yonyk.litlink.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@SQLDelete(sql = "UPDATE note SET deleted_at = CURRENT_TIMESTAMP WHERE note_id = ?")
@SQLRestriction("deleted_at is NULL")
@Entity
@Table(name = "note")
public class Note extends BaseEntity {
  @Id
  private long noteId;

  @ManyToOne
  @JoinColumn(name = "bookmark_id")
  private BookMark bookmark;

  private String content;
}
