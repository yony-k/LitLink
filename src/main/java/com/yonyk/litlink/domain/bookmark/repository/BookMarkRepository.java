package com.yonyk.litlink.domain.bookmark.repository;

import com.yonyk.litlink.domain.bookmark.entity.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

}
