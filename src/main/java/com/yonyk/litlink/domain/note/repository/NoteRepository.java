package com.yonyk.litlink.domain.note.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yonyk.litlink.domain.note.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

  // 노트 소유주 확인(BookMark, Member 테이블 조인 후 컬럼 수를 이용해서 boolean 반환)
  @Query(
      "SELECT COUNT(n) > 0 FROM Note n "
          + "JOIN n.bookmark b "
          + "JOIN b.member m "
          + "WHERE n.noteId = :noteId "
          + "AND m.memberId = :memberId")
  boolean existsByMemberIdAndNoteId(@Param("memberId") Long memberId, @Param("noteId") Long noteId);

  // 노트 목록 조회
  List<Note> findByBookmarkBookmarkId(Long bookmarkId);
}
