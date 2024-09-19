package com.yonyk.litlink.domain.bookmark.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BookMarkDTO(
        long bookMarkId,
        long memberId,
        long bookId,
        String title,
        String link,
        String image,
        String author,
        int discount,
        String publisher,
        String isbn,
        String description,
        String pubdate,
        int likeCount
) {
}
