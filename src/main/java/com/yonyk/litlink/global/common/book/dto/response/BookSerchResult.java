package com.yonyk.litlink.global.common.book.dto.response;

import java.util.List;

public record BookSerchResult(int total, int start, int display, List<BookDTO> items) {}
