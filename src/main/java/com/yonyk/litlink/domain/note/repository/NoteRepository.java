package com.yonyk.litlink.domain.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yonyk.litlink.domain.note.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

  @Query(
      "SELECT COUNT(n) > 0 FROM Note n "
          + "JOIN n.bookmark b "
          + "JOIN b.member m "
          + "WHERE n.noteId = :noteId "
          + "AND m.memberId = :memberId")
  boolean existsByMemberIdAndNoteId(@Param("memberId") Long memberId, @Param("noteId") Long noteId);
}
