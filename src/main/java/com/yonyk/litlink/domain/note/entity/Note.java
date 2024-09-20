package com.yonyk.litlink.domain.note.entity;

import jakarta.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.yonyk.litlink.domain.bookmark.entity.BookMark;
import com.yonyk.litlink.domain.note.dto.request.UpdateNoteDTO;
import com.yonyk.litlink.global.common.BaseEntity;

import lombok.*;

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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long noteId;

  @ManyToOne
  @JoinColumn(name = "bookmark_id")
  private BookMark bookmark;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String content;

  public Note updateNote(UpdateNoteDTO noteDTO) {
    return Note.builder()
        .noteId(noteId)
        .bookmark(bookmark)
        .title(noteDTO.title() != null ? noteDTO.title() : title)
        .content(noteDTO.content() != null ? noteDTO.content() : content)
        .build();
  }
}
