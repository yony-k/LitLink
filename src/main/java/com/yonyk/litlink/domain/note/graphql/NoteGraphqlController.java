package com.yonyk.litlink.domain.note.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import com.yonyk.litlink.domain.note.dto.request.CreateNoteDTO;
import com.yonyk.litlink.domain.note.dto.request.UpdateNoteDTO;
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
}
