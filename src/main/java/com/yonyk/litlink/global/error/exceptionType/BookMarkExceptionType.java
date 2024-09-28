package com.yonyk.litlink.global.error.exceptionType;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum BookMarkExceptionType implements ExceptionType {
  // 404 Not Found
  BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "저장된 내역이 존재하지 않습니다."),
  SHARETOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "공유 내역이 존재하지 않습니다."),

  // 400 Bad Request
  BOOKMARK_ALREADY_SAVED(HttpStatus.BAD_REQUEST, "이미 저장된 책 입니다.");

  private final HttpStatus status;
  private final String message;

  @Override
  public HttpStatus status() {
    return this.status;
  }

  @Override
  public String message() {
    return this.message;
  }
}
