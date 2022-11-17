package jk.program.library_manager.controller;

import jk.program.library_manager.exception.InvalidWriterException;
import jk.program.library_manager.exception.WriterNotFoundException;
import jk.program.library_manager.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class WriterControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriterControllerAdvice.class);

    @ExceptionHandler(value = InvalidWriterException.class)
    public ResponseEntity<ErrorResponse> handleInvalidWriterException(InvalidWriterException invalidWriterException) {
        ErrorResponse errorResponse = new ErrorResponse(invalidWriterException.getErrors());
        LOGGER.error("InvalidWriterException: {}", errorResponse);
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(value = WriterNotFoundException.class)
    public ResponseEntity<Void> handleWriterNotFoundException(WriterNotFoundException writerNotFoundException) {
        return ResponseEntity.notFound()
                .build();
    }
}
