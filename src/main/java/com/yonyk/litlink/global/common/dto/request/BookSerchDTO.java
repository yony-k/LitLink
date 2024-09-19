package com.yonyk.litlink.global.common.dto.request;

public record BookSerchDTO(
        String query,
        int display,
        int start,
        String sort
) {
}
