package com.sprint.mission.discodeit.exception;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sprint.mission.discodeit.exception.base.DiscodeitException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    HttpStatus status = HttpStatus.BAD_REQUEST;

    Map<String, Object> details = new LinkedHashMap<>();
    Map<String, List<String>> fieldErrors = e.getBindingResult()
        .getFieldErrors()
        .stream()
        .collect(LinkedHashMap::new,
            (errors, fieldError) -> errors.computeIfAbsent(fieldError.getField(),
                key -> new java.util.ArrayList<>())
                .add(resolveValidationMessage(fieldError)),
            Map::putAll);

    if (!fieldErrors.isEmpty()) {
      details.put("fieldErrors", fieldErrors);
    }

    List<String> globalErrors = e.getBindingResult()
        .getGlobalErrors()
        .stream()
        .map(error -> error.getDefaultMessage() == null ? "Invalid request." : error.getDefaultMessage())
        .toList();

    if (!globalErrors.isEmpty()) {
      details.put("globalErrors", globalErrors);
    }

    ErrorResponse errorResponse = new ErrorResponse(
        Instant.now(),
        "VALIDATION_ERROR",
        "Validation failed.",
        details,
        e.getClass().getSimpleName(),
        status.value()
    );

    return ResponseEntity
        .status(status)
        .body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    ErrorResponse errorResponse = new ErrorResponse(
            Instant.now(),
            "INTERNAL_SERVER_ERROR",
            "Internal server error",
            Map.of(),
            e.getClass().getSimpleName(),
            status.value()
    );
    return ResponseEntity
            .status(status)
            .body(errorResponse);
  }

  private String resolveValidationMessage(FieldError fieldError) {
    return fieldError.getDefaultMessage() == null ? "Invalid value." : fieldError.getDefaultMessage();
  }
}
