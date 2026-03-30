package com.sprint.mission.discodeit.exception;

import java.util.NoSuchElementException;

import com.sprint.mission.discodeit.exception.base.DiscodeitException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DiscodeitException.class)
  public ResponseEntity<ErrorResponse> handleDiscodeitException(DiscodeitException e) {
    HttpStatus status = e.getErrorCode().getStatus();

    ErrorResponse errorResponse = new ErrorResponse(
            e.getTimestamp(),
            e.getErrorCode().name(),
            e.getMessage(),
            e.getDetails(),
            e.getClass().getSimpleName(),
            status.value()
    );

    return ResponseEntity
            .status(status)
            .body(errorResponse);
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<String> handleException(NoSuchElementException e) {
    e.printStackTrace();
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e) {
    e.printStackTrace();
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(e.getMessage());
  }
}
