package com.yonyk.litlink.domain.note.dto.response;

import lombok.Builder;

@Builder
public record NoteDTO(long noteId, long bookMarkId, String title, String content) {}
