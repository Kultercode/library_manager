package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import jk.program.library_manager.dto.WriterDTO;
import jk.program.library_manager.entity.Writer;
import jk.program.library_manager.exception.WriterNotFoundException;
import jk.program.library_manager.repository.WriterRepository;
import jk.program.library_manager.service.impl.WriterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class WriterServiceImplTest {

    private static final Long WRITER_ID = 1L;
    private static final Writer WRITER = new Writer();
    private static final WriterDTO WRITER_DTO = new WriterDTO();

    @Mock
    private WriterRepository writerRepository;
    @Mock
    private ModelMapper modelMapper;
    private WriterServiceImpl underTest;

    @BeforeEach
    public void setUp() {
        underTest = new WriterServiceImpl(writerRepository, modelMapper);
    }

    @Test
    public void testFindAllShouldReturnAllWriter() {
        given(writerRepository.findAll()).willReturn(Collections.singletonList(WRITER));
        given(modelMapper.map(WRITER, WriterDTO.class)).willReturn(WRITER_DTO);

        List<WriterDTO> result = underTest.findAll();

        List<WriterDTO> expected = Collections.singletonList(WRITER_DTO);

        assertEquals(expected, result);
    }

    @Test
    public void testFindByIdShouldReturnWriterInOptionalIfItExists() {
        given(writerRepository.findById(WRITER_ID)).willReturn(Optional.of(WRITER));
        given(modelMapper.map(WRITER, WriterDTO.class)).willReturn(WRITER_DTO);

        Optional<WriterDTO> result = underTest.findById(WRITER_ID);

        Optional<WriterDTO> expected = Optional.of(WRITER_DTO);

        assertEquals(expected, result);
    }

    @Test
    public void testFindByIdShouldReturnEmptyOptionalIfWriterNotFound() {
        given(writerRepository.findById(WRITER_ID)).willReturn(Optional.empty());

        Optional<WriterDTO> result = underTest.findById(WRITER_ID);

        Optional<WriterDTO> expected = Optional.empty();

        assertEquals(expected, result);
    }

    @Test
    public void testUpdateShouldUpdateAndReturnWriterIfItExists() {
        WriterDTO writerDTOToUpdate = WriterDTO.builder()
                .id(WRITER_ID)
                .build();
        Writer storedWriter = new Writer();
        Optional<Writer> storedWriterOptional = Optional.of(storedWriter);
        Writer writerToUpdate = new Writer();
        Writer updatedWriter = new Writer();
        WriterDTO updatedWriterDTO = new WriterDTO();

        given(writerRepository.findById(WRITER_ID)).willReturn(storedWriterOptional);
        given(modelMapper.map(writerDTOToUpdate, Writer.class)).willReturn(writerToUpdate);
        given(writerRepository.save(writerToUpdate)).willReturn(updatedWriter);
        given(modelMapper.map(updatedWriter, WriterDTO.class)).willReturn(updatedWriterDTO);

        WriterDTO result = underTest.update(writerDTOToUpdate);

        assertEquals(updatedWriterDTO, result);
    }

    @Test
    public void testUpdateShouldThrowExceptionIfWriterNotFound() {
        WriterDTO writerDTOToUpdate = WriterDTO.builder()
                .id(WRITER_ID)
                .build();

        given(writerRepository.findById(WRITER_ID)).willReturn(Optional.empty());

        assertThrows(WriterNotFoundException.class, () -> {
            underTest.update(writerDTOToUpdate);
        });
    }

    @Test
    public void testDeleteShouldDeleteRequestedWriter() {
        Writer writerToDelete = new Writer();
        writerToDelete.setId(WRITER_ID);
        Optional<Writer> writerToDeleteOptional = Optional.of(writerToDelete);

        given(writerRepository.findById(WRITER_ID)).willReturn(writerToDeleteOptional);

        underTest.delete(WRITER_ID);

        verify(writerRepository).delete(writerToDelete);
    }

    @Test
    public void testDeleteShouldThrowExceptionIfWriterNotFound() {

        given(writerRepository.findById(WRITER_ID)).willReturn(Optional.empty());

        assertThrows(WriterNotFoundException.class, () -> {
            underTest.delete(WRITER_ID);
        });
    }
}
