package com.yonyk.litlink.domain.note.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyk.litlink.domain.bookmark.entity.BookMark;
import com.yonyk.litlink.domain.bookmark.repository.BookMarkRepository;
import com.yonyk.litlink.domain.note.dto.request.CreateNoteDTO;
import com.yonyk.litlink.domain.note.dto.request.UpdateNoteDTO;
import com.yonyk.litlink.domain.note.dto.response.NoteDTO;
import com.yonyk.litlink.domain.note.entity.Note;
import com.yonyk.litlink.domain.note.repository.NoteRepository;
import com.yonyk.litlink.global.error.CustomException;
import com.yonyk.litlink.global.error.exceptionType.BookMarkExceptionType;
import com.yonyk.litlink.global.error.exceptionType.NoteExceptionType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoteService {

  private final BookMarkRepository bookMarkRepository;
  private final NoteRepository noteRepository;

  // 노트 생성
  public void saveNote(long memberId, CreateNoteDTO createNoteDTO) {
    // 북마크 가져오기
    BookMark bookMark = findBookMark(memberId, createNoteDTO.bookMarkId());
    // 노트 생성 후 저장
    Note note =
        Note.builder()
            .bookmark(bookMark)
            .title(createNoteDTO.title())
            .content(createNoteDTO.content())
            .build();
    noteRepository.save(note);
  }

  // 노트 수정
  @Transactional
  public void updateNote(long memberId, UpdateNoteDTO updateNoteDTO) {
    // 노트 가져오기
    Note note = findNote(updateNoteDTO.noteId());
    // 노트 소유주 확인
    checkAuthor(memberId, updateNoteDTO.noteId());
    // 노트 수정
    note = note.updateNote(updateNoteDTO);
    // 업데이트
    noteRepository.save(note);
  }

  // 노트 삭제
  @Transactional
  public void deleteNote(long memberId, long noteId) {
    // 노트 소유주 확인 및 존재 확인
    checkAuthor(memberId, noteId);
    // 노트 삭제
    noteRepository.deleteById(noteId);
  }

  // 노트 목록 조회
  public List<NoteDTO> getNotes(long bookmarkId) {
    // bookmarkId 기준으로 조회 후 NoteDTO 로 변환하여 반환
    return noteRepository.findByBookmarkBookmarkId(bookmarkId).stream()
        .map(NoteDTO::toNoteDTO)
        .toList();
  }

  // 노트 존재 확인
  private Note findNote(long noteId) {
    // noteId 에 해당하는 Note 가 있는지 확인
    Optional<Note> note = noteRepository.findById(noteId);
    // 없다면 예외 반환
    if (note.isEmpty()) throw new CustomException(NoteExceptionType.NOTE_NOT_FOUND);
    // 있다면 Note 반환
    return note.get();
  }

  // 노트 소유주 확인
  private void checkAuthor(long memberId, long noteId) {
    boolean checkd = noteRepository.existsByMemberIdAndNoteId(memberId, noteId);
    if (!checkd) throw new CustomException(NoteExceptionType.NOTE_NOT_FOUND);
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
