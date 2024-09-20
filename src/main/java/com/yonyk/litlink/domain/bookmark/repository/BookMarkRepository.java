package com.yonyk.litlink.domain.bookmark.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yonyk.litlink.domain.bookmark.entity.BookMark;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

  List<BookMark> findByMemberMemberId(Long memberId);

  Optional<BookMark> findByBookmarkIdAndMemberMemberId(Long bookMarkId, Long memberId);
}
