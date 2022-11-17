package jk.program.library_manager.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;

import jk.program.library_manager.dto.WriterDTO;
import jk.program.library_manager.exception.InvalidWriterException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import jk.program.library_manager.service.WriterService;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/writers")
public class WriterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriterController.class);

    private final WriterService writerService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<WriterDTO>> findAll() {
        LOGGER.info("Received writers findALl request.");
        return ResponseEntity.ok(writerService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<WriterDTO> create(@RequestBody @Valid WriterDTO writerDTO, BindingResult bindingResult) {
        LOGGER.info("Received writers create request.");

        checkErrors(bindingResult);
        WriterDTO savedWriter = writerService.create(writerDTO);
        LOGGER.info("Successfully created a writer.");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedWriter);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<WriterDTO> findById(@PathVariable(name = "id") Long identifier) {
        LOGGER.info("Received writers/id findById request.");
        Optional<WriterDTO> optionalWriterDTO = writerService.findById(identifier);

        ResponseEntity<WriterDTO> response;

        if (optionalWriterDTO.isPresent()) {
            response = ResponseEntity.ok(optionalWriterDTO.get());
            LOGGER.info("Successfully found the writer.");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            LOGGER.error("No writer was found with the given ID!");
        }

        return response;
    }

    @RequestMapping(path = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<WriterDTO>> findByName(@PathVariable(name = "name") String name) {
        LOGGER.info("Received writers/name findByName request.");
        List<WriterDTO> listWriterDTO = writerService.findByName(name);

        LOGGER.info("Successfully searched by name.");
        return ResponseEntity.ok(listWriterDTO);
    }

    @RequestMapping(path = "/birthDate/{birthDate}", method = RequestMethod.GET)
    public ResponseEntity<List<WriterDTO>> findByBirthDate(@PathVariable(name = "birthDate") Date birthDate) {
        LOGGER.info("Received writers/birthDate findByBirthDate request.");
        List<WriterDTO> listWriterDTO = writerService.findByBirthDate(birthDate);

        LOGGER.info("Successfully searched by birthDate.");
        return ResponseEntity.ok(listWriterDTO);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<WriterDTO> update(@RequestBody @Valid WriterDTO writerDTO, BindingResult bindingResult) {
        LOGGER.info("Received writers update request.");

        checkErrors(bindingResult);
        WriterDTO updatedWriter = writerService.update(writerDTO);
        LOGGER.info("Successfully updated the writer.");

        return ResponseEntity.ok()
                .body(updatedWriter);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        LOGGER.info("Received writers delete request.");

        writerService.delete(id);

        LOGGER.info("Successfully deleted the writer.");
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

            throw new InvalidWriterException("Invalid writer", errors);
        }
    }

    private String fieldErrorToMessage(FieldError fieldError) {
        return fieldError.getField() + " - " + fieldError.getDefaultMessage();
    }
}
