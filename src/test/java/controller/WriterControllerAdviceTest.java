package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import jk.program.library_manager.controller.WriterControllerAdvice;
import jk.program.library_manager.exception.InvalidWriterException;
import jk.program.library_manager.exception.WriterNotFoundException;
import jk.program.library_manager.response.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class WriterControllerAdviceTest {

    private static final String EXCEPTION_MESSAGE = "exceptionMessage";

    private static final String ERROR_MESSAGE_1 = "errorMessage1";
    private static final String ERROR_MESSAGE_2 = "errorMessage2";
    private static final List<String> ERROR_MESSAGE_LIST = List.of(ERROR_MESSAGE_1, ERROR_MESSAGE_2);
    private static final InvalidWriterException INVALID_WRITER_REQUEST_EXCEPTION =
            new InvalidWriterException(EXCEPTION_MESSAGE, ERROR_MESSAGE_LIST);

    private static final WriterNotFoundException WRITER_NOT_FOUND_EXCEPTION =
            new WriterNotFoundException(EXCEPTION_MESSAGE);

    private WriterControllerAdvice underTest;

    @BeforeEach
    public void setUp() {
        underTest = new WriterControllerAdvice();
    }

    @Test
    public void testInvalidRequestHandlerShouldReturnBadRequestErrorWithErrorMessagesFromException() {

        ResponseEntity<ErrorResponse> result = underTest.handleInvalidWriterException(INVALID_WRITER_REQUEST_EXCEPTION);

        ResponseEntity<ErrorResponse> expected = ResponseEntity.badRequest()
                .body(new ErrorResponse(ERROR_MESSAGE_LIST));

        assertEquals(expected, result);
    }

    @Test
    public void testWriterNotFoundHandlerShouldReturnEmptyResponseWithNotFoundStatusCode() {

        ResponseEntity<Void> result = underTest.handleWriterNotFoundException(WRITER_NOT_FOUND_EXCEPTION);

        ResponseEntity<Void> expected = ResponseEntity.notFound().build();

        assertEquals(expected, result);
    }
}
