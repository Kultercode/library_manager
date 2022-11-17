package jk.program.library_manager.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import jk.program.library_manager.dto.BookDTO;
import jk.program.library_manager.exception.InvalidBookException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import jk.program.library_manager.service.BookService;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/books")
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<BookDTO>> findAll() {
        LOGGER.info("Received books findALl request.");
        return ResponseEntity.ok(bookService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BookDTO> create(@RequestBody @Valid BookDTO bookDTO, BindingResult bindingResult) {
        LOGGER.info("Received books create request.");

        checkErrors(bindingResult);
        BookDTO savedBook = bookService.create(bookDTO);
        LOGGER.info("Successfully created a book.");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedBook);
    }

    @RequestMapping(path = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<BookDTO> findById(@PathVariable(name = "id") Long identifier) {
        LOGGER.info("Received books/id findById request.");
        Optional<BookDTO> optionalBookDTO = bookService.findById(identifier);

        ResponseEntity<BookDTO> response;

        if (optionalBookDTO.isPresent()) {
            response = ResponseEntity.ok(optionalBookDTO.get());
            LOGGER.info("Successfully found the book.");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            LOGGER.error("No book was found with the given ID!");
        }

        return response;
    }

    @RequestMapping(path = "/title/{title}", method = RequestMethod.GET)
    public ResponseEntity<List<BookDTO>> findByTitle(@PathVariable(name = "title") String title) {
        LOGGER.info("Received books/title findByTitle request.");
        List<BookDTO> listBookDTO = bookService.findByTitle(title);

        LOGGER.info("Successfully searched by title.");
        return ResponseEntity.ok(listBookDTO);
    }

    @RequestMapping(path = "/releaseDate/{releaseDate}", method = RequestMethod.GET)
    public ResponseEntity<List<BookDTO>> findByReleaseDate(@PathVariable(name = "releaseDate") Date releaseDate) {
        LOGGER.info("Received books/releaseDate findByReleaseDate request.");
        List<BookDTO> listBookDTO = bookService.findByReleaseDate(releaseDate);

        LOGGER.info("Successfully searched by releaseDate.");
        return ResponseEntity.ok(listBookDTO);
    }


    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<BookDTO> update(@RequestBody @Valid BookDTO bookDTO, BindingResult bindingResult) {
        LOGGER.info("Received books update request.");

        checkErrors(bindingResult);
        BookDTO updatedBook = bookService.update(bookDTO);
        LOGGER.info("Successfully updated the book.");

        return ResponseEntity.ok()
                .body(updatedBook);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        LOGGER.info("Received books delete request.");

        bookService.delete(id);

        LOGGER.info("Successfully deleted the book.");
        return ResponseEntity.noContent().build();
    }

    private void checkErrors(BindingResult bindingResult) {
        LOGGER.info("bindingResult has errors = {}", bindingResult.hasErrors());
        LOGGER.info("errors = {}", bindingResult.getAllErrors());

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(this::fieldErrorToMessage)
                    .collect(Collectors.toList());

            throw new InvalidBookException("Invalid book", errors);
        }
    }

    private String fieldErrorToMessage(FieldError fieldError) {
        return fieldError.getField() + " - " + fieldError.getDefaultMessage();
    }
}
