package at.technikumwien.lernbegleiter.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(ResponseStatusException.class)
    protected ResponseEntity<ExceptionResponse> handleResponseStatusException(ResponseStatusException ex) {
        log.error("ErrorHandler caught an ResponseStatusException", ex);
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionResponse(ex.getStatus(), ex.getReason()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionResponse> handleException(Exception ex) {
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
