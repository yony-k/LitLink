package com.yonyk.litlink.domain.note.dto.response;

import com.yonyk.litlink.domain.note.entity.Note;

import lombok.Builder;

@Builder
public record NoteDTO(long noteId, long bookMarkId, String title, String content) {
  public static NoteDTO toNoteDTO(Note note) {
    return NoteDTO.builder()
        .noteId(note.getNoteId())
        .bookMarkId(note.getBookmark().getBookmarkId())
        .title(note.getTitle())
        .content(note.getContent())
        .build();
  }
}
