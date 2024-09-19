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
    // memberId로 목록 조회 후 BookMarkDTO 로 변환하여 반환
    return bookMarkRepository.findByMemberMemberId(memberId).stream()
            .map(BookMarkDTO::toBookMarkDTO)
            .toList();
  }

  // 북마크 상세 조회
  public BookMarkDTO getBookMark(long bookMarkId) {
    // bookMarkId에 해당하는 BookMark 가 있는지 확인
    Optional<BookMark> findBookMark = bookMarkRepository.findById(bookMarkId);
    // 없다면 예외 반환
    if(findBookMark.isEmpty()) throw new CustomException(BookMarkExceptionType.BOOKMARK_NOT_FOUND);
    // BookMarkDTO 로 변환하여 반환
    return BookMarkDTO.toBookMarkDTO(findBookMark.get());
  }

  // 북마크 좋아요 표시
  @Transactional
  public void likeBookMark(long bookMarkId) {
    // bookMarkId에 해당하는 BookMark 가 있는지 확인
    Optional<BookMark> findBookMark = bookMarkRepository.findById(bookMarkId);
    // 없다면 예외 반환
    if(findBookMark.isEmpty()) throw new CustomException(BookMarkExceptionType.BOOKMARK_NOT_FOUND);
    // liked 필드를 true로 값을 바꾸고 저장
    findBookMark.get().like();
    bookMarkRepository.save(findBookMark.get());
    // Book 엔티티의 likeCount 변경
    findBookMark.get().getBook().incrementLikeCount();
    bookRepository.save(findBookMark.get().getBook());
  }
}
