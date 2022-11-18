package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.*;

import jk.program.library_manager.controller.WriterController;
import jk.program.library_manager.dto.BookDTO;
import jk.program.library_manager.dto.WriterDTO;
import jk.program.library_manager.service.WriterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@ExtendWith(MockitoExtension.class)
public class WriterControllerTest {
    
    private static final Long WRITER_ID = 1L;
    private static final String WRITER_NAME = "George Orwell";
    private static final Date WRITER_BIRTHDATE = new GregorianCalendar(1903, Calendar.JUNE, 25).getTime();
    private static final WriterDTO WRITER_DTO = new WriterDTO();
    private static final WriterDTO REQUEST = new WriterDTO();
    private static final WriterDTO CREATED_WRITER = new WriterDTO();
    private static final WriterDTO UPDATED_WRITER = new WriterDTO();

    @Mock
    private WriterService writerService;
    @Mock
    private BindingResult bindingResult;
    private WriterController underTest;

    @BeforeEach
    public void setUp() {
        underTest = new WriterController(writerService);
    }

    @Test
    public void testFindAllShouldReturnListOfWriter() {
        List<WriterDTO> writerDTOList = Collections.singletonList(WRITER_DTO);

        given(writerService.findAll()).willReturn(writerDTOList);

        ResponseEntity<List<WriterDTO>> result = underTest.findAll();

        ResponseEntity<List<WriterDTO>> expected = ResponseEntity.ok().body(writerDTOList);

        assertEquals(expected, result);
    }

    @Test
    public void testFindByIdShouldReturnWriterIfItExists() {
        Optional<WriterDTO> writerDTOOptional = Optional.of(WRITER_DTO);

        given(writerService.findById(WRITER_ID)).willReturn(writerDTOOptional);

        ResponseEntity<WriterDTO> result = underTest.findById(WRITER_ID);

        ResponseEntity<WriterDTO> expected = ResponseEntity.ok(WRITER_DTO);

        assertEquals(expected, result);
    }

    @Test
    public void testFindByIdShouldReturnNotFoundIfWriterDoesNotExist() {
        given(writerService.findById(WRITER_ID)).willReturn(Optional.empty());

        ResponseEntity<WriterDTO> result = underTest.findById(WRITER_ID);

        ResponseEntity<WriterDTO> expected = ResponseEntity.notFound().build();

        assertEquals(expected, result);
    }

    @Test
    public void testFindByTitleShouldReturnListOfBook() {
        List<WriterDTO> writerDTOList = Collections.singletonList(WRITER_DTO);

        given(writerService.findByName(WRITER_NAME)).willReturn(writerDTOList);

        ResponseEntity<List<WriterDTO>> result = underTest.findByName(WRITER_NAME);

        ResponseEntity<List<WriterDTO>> expected = ResponseEntity.ok(writerDTOList);

        assertEquals(expected, result);
    }

    @Test
    public void testFindByBirthDateShouldReturnListOfBook() {
        List<WriterDTO> writerDTOList = Collections.singletonList(WRITER_DTO);

        given(writerService.findByBirthDate(WRITER_BIRTHDATE)).willReturn(writerDTOList);

        ResponseEntity<List<WriterDTO>> result = underTest.findByBirthDate(WRITER_BIRTHDATE);

        ResponseEntity<List<WriterDTO>> expected = ResponseEntity.ok(writerDTOList);

        assertEquals(expected, result);
    }

    @Test
    public void testFindByNameAndBirthDateShouldReturnListOfBook() {
        List<WriterDTO> writerDTOList = Collections.singletonList(WRITER_DTO);

        given(writerService.findByNameAndBirthDate(WRITER_NAME, WRITER_BIRTHDATE)).willReturn(writerDTOList);

        ResponseEntity<List<WriterDTO>> result = underTest.findByNameAndBirthDate(WRITER_NAME, WRITER_BIRTHDATE);

        ResponseEntity<List<WriterDTO>> expected = ResponseEntity.ok(writerDTOList);

        assertEquals(expected, result);
    }

    @Test
    public void testCreateShouldCreateAndReturnNewWriterIfRequestIsValid() {
        given(writerService.create(REQUEST)).willReturn(CREATED_WRITER);

        ResponseEntity<WriterDTO> result = underTest.create(REQUEST, bindingResult);

        ResponseEntity<WriterDTO> expected = ResponseEntity.status(HttpStatus.CREATED)
                .body(CREATED_WRITER);

        assertEquals(expected, result);
    }

    @Test
    public void testUpdateShouldUpdateAndReturnUpdatedWriterIfRequestIsValid() {

        given(writerService.update(REQUEST)).willReturn(UPDATED_WRITER);

        ResponseEntity<WriterDTO> result = underTest.update(REQUEST, bindingResult);

        ResponseEntity<WriterDTO> expected = ResponseEntity.ok(UPDATED_WRITER);

        assertEquals(expected, result);
    }

    @Test
    public void testDeleteShouldDeleteTheRequestedWriter() {

        ResponseEntity<Void> result = underTest.delete(WRITER_ID);

        ResponseEntity<Void> expected = ResponseEntity.noContent().build();

        verify(writerService).delete(WRITER_ID);
        assertEquals(expected, result);
    }
}
