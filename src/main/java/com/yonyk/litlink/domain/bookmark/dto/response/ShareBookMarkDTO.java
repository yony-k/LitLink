package com.yonyk.litlink.domain.bookmark.dto.response;

import java.util.List;

import com.yonyk.litlink.domain.note.dto.response.NoteDTO;

public record ShareBookMarkDTO(BookMarkDTO bookMarkDTO, List<NoteDTO> noteDTOS) {}
