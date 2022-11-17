package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import jk.program.library_manager.controller.BookControllerAdvice;
import jk.program.library_manager.exception.BookNotFoundException;
import jk.program.library_manager.exception.InvalidBookException;
import jk.program.library_manager.response.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class BookControllerAdviceTest {

    private static final String EXCEPTION_MESSAGE = "exceptionMessage";

    private static final String ERROR_MESSAGE_1 = "errorMessage1";
    private static final String ERROR_MESSAGE_2 = "errorMessage2";
    private static final List<String> ERROR_MESSAGE_LIST = List.of(ERROR_MESSAGE_1, ERROR_MESSAGE_2);
    private static final InvalidBookException INVALID_BOOK_REQUEST_EXCEPTION =
            new InvalidBookException(EXCEPTION_MESSAGE, ERROR_MESSAGE_LIST);

    private static final BookNotFoundException BOOK_NOT_FOUND_EXCEPTION =
            new BookNotFoundException(EXCEPTION_MESSAGE);

    private BookControllerAdvice underTest;

    @BeforeEach
    public void setUp() {
        underTest = new BookControllerAdvice();
    }

    @Test
    public void testInvalidRequestHandlerShouldReturnBadRequestErrorWithErrorMessagesFromException() {

        ResponseEntity<ErrorResponse> result = underTest.handleInvalidBookException(INVALID_BOOK_REQUEST_EXCEPTION);

        ResponseEntity<ErrorResponse> expected = ResponseEntity.badRequest()
                .body(new ErrorResponse(ERROR_MESSAGE_LIST));

        assertEquals(expected, result);
    }

    @Test
    public void testBookNotFoundHandlerShouldReturnEmptyResponseWithNotFoundStatusCode() {

        ResponseEntity<Void> result = underTest.handleBookNotFoundException(BOOK_NOT_FOUND_EXCEPTION);

        ResponseEntity<Void> expected = ResponseEntity.notFound().build();

        assertEquals(expected, result);
    }
}
