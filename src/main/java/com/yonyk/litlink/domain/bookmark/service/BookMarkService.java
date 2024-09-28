package com.yonyk.litlink.domain.bookmark.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyk.litlink.domain.bookmark.dto.response.BookMarkDTO;
import com.yonyk.litlink.domain.bookmark.dto.response.ShareBookMarkDTO;
import com.yonyk.litlink.domain.bookmark.entity.BookMark;
import com.yonyk.litlink.domain.bookmark.repository.BookMarkRepository;
import com.yonyk.litlink.domain.member.entity.Member;
import com.yonyk.litlink.global.common.book.entity.Book;
import com.yonyk.litlink.global.common.book.service.BookService;
import com.yonyk.litlink.global.error.CustomException;
import com.yonyk.litlink.global.error.exceptionType.BookMarkExceptionType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookMarkService {

  private final BookMarkRepository bookMarkRepository;

  private final BookService bookService;
  private final BookMarkLikeService bookMarkLikeService;
  private final BookMarkShareService bookMarkShareService;

  // 북마크 저장
  @Transactional
  public void saveBookMark(Member member, String isbn) {
    // 책 정보 가져오기
    Book book = bookService.getOrSaveBook(isbn);
    // BookMark 엔티티 생성
    BookMark bookMark = BookMark.builder().member(member).book(book).build();
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
  public BookMarkDTO getBookMark(long memberId, long bookMarkId) {
    // BookMark 엔티티 가져오기
    BookMark bookMark = findBookMark(memberId, bookMarkId);
    // BookMarkDTO 로 변환하여 반환
    return BookMarkDTO.toBookMarkDTO(bookMark);
  }

  // 북마크 좋아요 표시
  public void likeBookMark(long memberId, long bookMarkId) {
    // BookMark 엔티티 가져오기
    BookMark bookMark = findBookMark(memberId, bookMarkId);
    // 좋아요 표시
    bookMarkLikeService.likeBookMark(bookMark);
  }

  // 북마크 삭제
  public void deleteBookMark(long memberId, long bookMarkId) {
    // BookMark 엔티티 가져오기
    BookMark bookMark = findBookMark(memberId, bookMarkId);
    // BookMark softdelete
    bookMarkRepository.delete(bookMark);
  }

  // 북마크 공유
  public String shareBookMark(long memberId, long bookMarkId) {
    // BookMark 엔티티 가져오기
    BookMark bookMark = findBookMark(memberId, bookMarkId);
    // 북마크 공유링크 반환
    return bookMarkShareService.shareBookMark(bookMark);
  }

  // 북마크 공유 기능을 이용한 북마크 상세 조회
  public ShareBookMarkDTO getShareBookMark(String shareToken) {
    // 공유 링크로 북마크 및 노트 반환
    return bookMarkShareService.getShareBookMark(shareToken);
  }

  // 북마크 존재 확인
  public BookMark findBookMark(long memberId, long bookMarkId) {
    // bookMarkId에 해당하는 BookMark 가 있는지 확인
    Optional<BookMark> findBookMark =
        bookMarkRepository.findByBookmarkIdAndMemberMemberId(bookMarkId, memberId);
    // 없다면 예외 반환
    if (findBookMark.isEmpty()) throw new CustomException(BookMarkExceptionType.BOOKMARK_NOT_FOUND);
    // 있다면 BookMark 반환
    return findBookMark.get();
  }
}
