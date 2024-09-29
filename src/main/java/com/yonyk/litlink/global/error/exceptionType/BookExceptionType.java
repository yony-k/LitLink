package com.yonyk.litlink.global.error.exceptionType;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum BookExceptionType implements ExceptionType {
  // 404 Not Found
  BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 책 입니다.");

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
