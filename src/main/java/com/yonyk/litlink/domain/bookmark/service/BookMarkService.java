package com.yonyk.litlink.domain.bookmark.service;

import com.yonyk.litlink.domain.bookmark.dto.response.BookMarkDTO;
import com.yonyk.litlink.domain.bookmark.entity.BookMark;
import com.yonyk.litlink.domain.bookmark.repository.BookMarkRepository;
import com.yonyk.litlink.domain.member.entity.Member;
import com.yonyk.litlink.global.common.book.repository.BookRepository;
import com.yonyk.litlink.global.common.book.dto.response.BookDTO;
import com.yonyk.litlink.global.common.book.entity.Book;
import com.yonyk.litlink.global.common.book.service.BookAPIService;
import com.yonyk.litlink.global.error.CustomException;
import com.yonyk.litlink.global.error.exceptionType.BookMarkExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookMarkService {

  private final BookAPIService bookAPIService;
  private final BookMarkRepository bookMarkRepository;
  private final BookRepository bookRepository;

  // 책 저장
  @Transactional
  public void saveBookMark(Member member, String isbn) {
    Book book = null;
    // DB에 저장된 책이 있는지 확인
    Optional<Book> findBook = bookRepository.findByIsbn(isbn);

    // 존재 여부에 따라 Book 엔티티 생성
    if (findBook.isPresent()) book = findBook.get();
    else {
      book = bookAPIService.serchBook(isbn).toBook();
      book = bookRepository.save(book);
    }

    // BookMark 엔티티 생성
    BookMark bookMark = BookMark.builder()
            .member(member)
            .book(book)
            .build();
    
    // BookMark 저장
    bookMarkRepository.save(bookMark);
  }

  // 북마크 목록 조회
  public List<BookMarkDTO> getBookMarks(long memberId) {
    return bookMarkRepository.findByMemberMemberId(memberId).stream()
            .map(BookMarkDTO::toBookMarkDTO)
            .toList();
  }

  // 북마크 상세 조회
  public BookMarkDTO getBookMark(long bookMarkId) {
    Optional<BookMark> findBookMark = bookMarkRepository.findById(bookMarkId);
    if(findBookMark.isEmpty()) throw new CustomException(BookMarkExceptionType.BOOKMARK_NOT_FOUND);
    return BookMarkDTO.toBookMarkDTO(findBookMark.get());
  }
}
