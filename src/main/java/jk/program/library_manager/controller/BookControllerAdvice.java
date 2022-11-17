package jk.program.library_manager.controller;

import jk.program.library_manager.exception.BookNotFoundException;
import jk.program.library_manager.exception.InvalidBookException;
import jk.program.library_manager.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = BookController.class)
public class BookControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookControllerAdvice.class);


    @ExceptionHandler(value = InvalidBookException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBookException(InvalidBookException invalidBookException) {
        ErrorResponse errorResponse = new ErrorResponse(invalidBookException.getErrors());
        LOGGER.error("InvalidBookException: {}", errorResponse);
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(value = BookNotFoundException.class)
    public ResponseEntity<Void> handleBookNotFoundException(BookNotFoundException bookNotFoundException) {
        return ResponseEntity.notFound()
                .build();
    }

}
