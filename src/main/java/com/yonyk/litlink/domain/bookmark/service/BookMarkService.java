package com.yonyk.litlink.domain.bookmark.service;

import com.yonyk.litlink.domain.bookmark.dto.response.BookMarkDTO;
import com.yonyk.litlink.domain.bookmark.entity.BookMark;
import com.yonyk.litlink.domain.bookmark.redis.ShareToken;
import com.yonyk.litlink.domain.bookmark.redis.ShareTokenRepository;
import com.yonyk.litlink.domain.bookmark.repository.BookMarkRepository;
import com.yonyk.litlink.domain.member.entity.Member;
import com.yonyk.litlink.global.common.book.repository.BookRepository;
import com.yonyk.litlink.global.common.book.entity.Book;
import com.yonyk.litlink.global.common.book.service.BookAPIService;
import com.yonyk.litlink.global.error.CustomException;
import com.yonyk.litlink.global.error.exceptionType.BookMarkExceptionType;
import com.yonyk.litlink.global.security.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
  private final ShareTokenRepository shareTokenRepository;
  private final JwtProvider jwtProvider;

  @Value("${app.share-url}")
  String baseUrl;

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
  public BookMarkDTO getBookMark(long memberId, long bookMarkId) {
    // BookMark 엔티티 가져오기
    BookMark bookMark = findBookMark(memberId, bookMarkId);
    // BookMarkDTO 로 변환하여 반환
    return BookMarkDTO.toBookMarkDTO(bookMark);
  }

  // 북마크 좋아요 표시
  @Transactional
  public void likeBookMark(long memberId, long bookMarkId) {
    // BookMark 엔티티 가져오기
    BookMark bookMark = findBookMark(memberId, bookMarkId);
    // liked 필드를 true로 값을 바꾸고 저장
    bookMark.like();
    bookMarkRepository.save(bookMark);
    // Book 엔티티의 likeCount 변경
    bookMark.getBook().incrementLikeCount();
    bookRepository.save(bookMark.getBook());
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
    // ShareToken 생성
    String shareToken = jwtProvider.getShareToken(bookMark.getBookmarkId());
    // Redis에 ShareToken 저장
    shareTokenRepository.save(new ShareToken(shareToken, bookMark.getBookmarkId()));
    // 공유 링크 반환
    return baseUrl + shareToken;
  }

  // 북마크 공유 기능을 이용한 북마크 상세 조회
  public BookMarkDTO getShareBookMark(String shareToken) {
    // Redis에서 ShareToken 찾아오기
    Optional<ShareToken> findToken = shareTokenRepository.findById(shareToken);
    // Redis에 없다면 예외 반환
    if (findToken.isEmpty()) throw new CustomException(BookMarkExceptionType.SHARETOKEN_NOT_FOUND);
    // Redis에 저장되어있던 bookMarkId로 BookMark 엔티티 가져오기
    Optional<BookMark> findBookMark = bookMarkRepository.findById(findToken.get().getBookMarkId());
    if (findBookMark.isEmpty()) throw new CustomException(BookMarkExceptionType.BOOKMARK_NOT_FOUND);
    // BookMarkDTO 변환 후 반환
    return BookMarkDTO.toBookMarkDTO(findBookMark.get());
  }

  // 북마크 존재 확인
  private BookMark findBookMark(long memberId, long bookMarkId) {
    // bookMarkId에 해당하는 BookMark 가 있는지 확인
    Optional<BookMark> findBookMark = bookMarkRepository.findByBookmarkIdAndMemberMemberId(bookMarkId, memberId);
    // 없다면 예외 반환
    if(findBookMark.isEmpty()) throw new CustomException(BookMarkExceptionType.BOOKMARK_NOT_FOUND);
    // 있다면 BookMark 반환
    return findBookMark.get();
  }
}
