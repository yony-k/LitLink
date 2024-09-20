package com.yonyk.litlink.domain.note.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yonyk.litlink.domain.bookmark.entity.BookMark;
import com.yonyk.litlink.domain.bookmark.repository.BookMarkRepository;
import com.yonyk.litlink.domain.note.dto.request.CreateNoteDTO;
import com.yonyk.litlink.domain.note.entity.Note;
import com.yonyk.litlink.domain.note.repository.NoteRepository;
import com.yonyk.litlink.global.error.CustomException;
import com.yonyk.litlink.global.error.exceptionType.BookMarkExceptionType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoteService {

  private final BookMarkRepository bookMarkRepository;
  private final NoteRepository noteRepository;

  // 노트 생성
  public void saveNote(long memberId, long bookMarkId, CreateNoteDTO createNoteDTO) {
    // 북마크 가져오기
    BookMark bookMark = findBookMark(memberId, bookMarkId);
    // 노트 생성 후 저장
    Note note =
        Note.builder()
            .bookmark(bookMark)
            .title(createNoteDTO.title())
            .content(createNoteDTO.content())
            .build();
    noteRepository.save(note);
  }

  // 북마크 존재 확인
  private BookMark findBookMark(long memberId, long bookMarkId) {
    // bookMarkId에 해당하는 BookMark 가 있는지 확인
    Optional<BookMark> findBookMark =
        bookMarkRepository.findByBookmarkIdAndMemberMemberId(bookMarkId, memberId);
    // 없다면 예외 반환
    if (findBookMark.isEmpty()) throw new CustomException(BookMarkExceptionType.BOOKMARK_NOT_FOUND);
    // 있다면 BookMark 반환
    return findBookMark.get();
  }
}
