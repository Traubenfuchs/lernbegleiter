package at.technikumwien.lernbegleiter.controller;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
  @ExceptionHandler(ResponseStatusException.class)
  protected ResponseEntity<ExceptionResponse> handle(ResponseStatusException ex) {
    log.error("ErrorHandler caught an ResponseStatusException", ex);
    return ResponseEntity.status(ex.getStatus()).body(new ExceptionResponse(ex.getStatus(), ex.getReason()));
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ExceptionResponse> handle(Exception ex) {
    log.error("ErrorHandler caught an Exception", ex);
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ExceptionResponse(INTERNAL_SERVER_ERROR, ex.getMessage()));
  }

  @Data
  private static class ExceptionResponse {
    public ExceptionResponse(HttpStatus httpStatus, String message) {
      this.error = httpStatus.name();
      this.code = httpStatus.value();
      this.message = message;
    }

    private int code;
    private String error;
    private String message;
  }
}
