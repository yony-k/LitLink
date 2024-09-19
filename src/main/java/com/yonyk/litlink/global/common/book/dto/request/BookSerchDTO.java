package com.yonyk.litlink.global.common.book.dto.request;

import lombok.Builder;

@Builder
public record BookSerchDTO(
        String query,
        int display,
        int start,
        String sort
) {
}
