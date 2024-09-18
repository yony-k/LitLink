package com.yonyk.litlink.global.error;

import com.yonyk.litlink.global.error.exceptionType.ExceptionType;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
  private final ExceptionType exceptionType;

  public CustomException(ExceptionType exceptionType) {
    super(exceptionType.message());
    this.exceptionType = exceptionType;
  }
}
