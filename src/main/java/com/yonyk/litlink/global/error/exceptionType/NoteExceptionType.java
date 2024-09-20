package com.yonyk.litlink.global.error.exceptionType;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum NoteExceptionType implements ExceptionType {
  // 404 Not Found
  NOTE_NOT_FOUND(HttpStatus.NOT_FOUND, "노트가 존재하지 않습니다.");

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
