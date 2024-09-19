package com.yonyk.litlink.domain.bookmark.repository;

import com.yonyk.litlink.domain.bookmark.entity.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

  List<BookMark> findByMemberMemberId(Long memberId);

}
