package com.yonyk.litlink.domain.bookmark.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yonyk.litlink.domain.bookmark.entity.BookMark;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

  // 북마크 목록 조회
  List<BookMark> findByMemberMemberId(Long memberId);

  // 북마크 상세 조회
  Optional<BookMark> findByBookmarkIdAndMemberMemberId(Long bookMarkId, Long memberId);

  // 북마크 존재 확인
  boolean existsByMemberMemberIdAndBookIsbn(Long memberId, String isbn);
}
