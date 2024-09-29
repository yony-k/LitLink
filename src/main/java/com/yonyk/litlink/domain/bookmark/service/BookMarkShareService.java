package com.yonyk.litlink.domain.bookmark.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yonyk.litlink.domain.bookmark.dto.response.BookMarkDTO;
import com.yonyk.litlink.domain.bookmark.dto.response.ShareBookMarkDTO;
import com.yonyk.litlink.domain.bookmark.entity.BookMark;
import com.yonyk.litlink.domain.bookmark.redis.ShareToken;
import com.yonyk.litlink.domain.bookmark.redis.ShareTokenRepository;
import com.yonyk.litlink.domain.bookmark.repository.BookMarkRepository;
import com.yonyk.litlink.domain.note.dto.response.NoteDTO;
import com.yonyk.litlink.domain.note.repository.NoteRepository;
import com.yonyk.litlink.global.error.CustomException;
import com.yonyk.litlink.global.error.exceptionType.BookMarkExceptionType;
import com.yonyk.litlink.global.security.util.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookMarkShareService {

  private final BookMarkRepository bookMarkRepository;
  private final ShareTokenRepository shareTokenRepository;
  private final NoteRepository noteRepository;
  private final JwtProvider jwtProvider;

  @Value("${app.share-url}")
  String baseUrl;

  // 북마크 공유
  public String shareBookMark(BookMark bookMark) {
    // ShareToken 생성
    String shareToken = jwtProvider.getShareToken(bookMark.getBookmarkId());
    // Redis에 ShareToken 저장
    shareTokenRepository.save(new ShareToken(shareToken, bookMark.getBookmarkId()));
    // 공유 링크 반환
    return baseUrl + shareToken;
  }

  // 북마크 공유 기능을 이용한 북마크 상세 조회
  public ShareBookMarkDTO getShareBookMark(String shareToken) {
    // Redis에서 ShareToken 찾아오기
    Optional<ShareToken> findToken = shareTokenRepository.findById(shareToken);
    // Redis에 없다면 예외 반환
    if (findToken.isEmpty()) throw new CustomException(BookMarkExceptionType.SHARETOKEN_NOT_FOUND);
    // Redis에 저장되어있던 bookMarkId로 BookMark 엔티티 가져오기
    Optional<BookMark> findBookMark = bookMarkRepository.findById(findToken.get().getBookMarkId());
    if (findBookMark.isEmpty()) throw new CustomException(BookMarkExceptionType.BOOKMARK_NOT_FOUND);
    // BookMark 엔티티 BookMarkDTO로 변환
    BookMarkDTO bookMarkDTO = BookMarkDTO.toBookMarkDTO(findBookMark.get());
    // BookMark에 연관되어있는 Note 목록 가져고 NoteDTO로 변환
    List<NoteDTO> noteDTOS =
        noteRepository.findByBookmarkBookmarkId(bookMarkDTO.bookMarkId()).stream()
            .map(NoteDTO::toNoteDTO)
            .toList();
    // ShareBookMarkDTO 생성 후 반환
    return new ShareBookMarkDTO(bookMarkDTO, noteDTOS);
  }
}
