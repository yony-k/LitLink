package com.yonyk.litlink.global.common.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record BookDTO(
        String title,
        String link,
        String image,
        String author,
        int discount,
        String publisher,
        String isbn,
        String description,
        @JsonFormat(pattern = "yyyyMMdd")
        LocalDate pubdate
) {
}
