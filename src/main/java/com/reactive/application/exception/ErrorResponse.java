package com.reactive.application.exception;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
  private LocalDateTime time;
  private String message;
  private Map<String, String> validationErrors;

  public ErrorResponse(LocalDateTime time, String message, String path) {
    this.time = time;
    this.message = message;
  }
}
