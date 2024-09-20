package com.yonyk.litlink.domain.note.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import com.yonyk.litlink.domain.note.dto.request.CreateNoteDTO;
import com.yonyk.litlink.domain.note.dto.request.UpdateNoteDTO;
import com.yonyk.litlink.domain.note.dto.response.NoteDTO;
import com.yonyk.litlink.domain.note.service.NoteService;
import com.yonyk.litlink.global.security.details.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class NoteGraphqlController {

  private final NoteService noteService;

  // 노트 생성
  @MutationMapping
  public String saveNote(
      @AuthenticationPrincipal PrincipalDetails principalDetails, @Argument CreateNoteDTO note) {
    noteService.saveNote(principalDetails.getMember().getMemberId(), note);
    return "노트 생성이 성공적으로 완료되었습니다.";
  }

  // 노트 수정
  @MutationMapping
  public String updateNote(
      @AuthenticationPrincipal PrincipalDetails principalDetails, @Argument UpdateNoteDTO note) {
    noteService.updateNote(principalDetails.getMember().getMemberId(), note);
    return "노트 수정이 성공적으로 완료되었습니다.";
  }

  // 노트 삭제
  @MutationMapping
  public String deleteNote(
      @AuthenticationPrincipal PrincipalDetails principalDetails, @Argument long noteId) {
    noteService.deleteNote(principalDetails.getMember().getMemberId(), noteId);
    return "노트 삭제가 성공적으로 완료되었습니다.";
  }

  // 노트 목록 조회
  @QueryMapping
  public List<NoteDTO> getNotes(
      @AuthenticationPrincipal PrincipalDetails principalDetails, @Argument long bookMarkId) {
    return noteService.getNotes(principalDetails.getMember().getMemberId(), bookMarkId);
  }

  // 노트 상세 조회
  @QueryMapping
  public NoteDTO getNote(
      @AuthenticationPrincipal PrincipalDetails principalDetails, @Argument long noteId) {
    return noteService.getNote(principalDetails.getMember().getMemberId(), noteId);
  }
}
