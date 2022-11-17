package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import jk.program.library_manager.dto.BookDTO;
import jk.program.library_manager.entity.Book;
import jk.program.library_manager.exception.BookNotFoundException;
import jk.program.library_manager.repository.BookRepository;
import jk.program.library_manager.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    private static final Long BOOK_ID = 1L;
    private static final Book BOOK = new Book();
    private static final BookDTO BOOK_DTO = new BookDTO();

    @Mock
    private BookRepository bookRepository;
    @Mock
    private ModelMapper modelMapper;
    private BookServiceImpl underTest;

    @BeforeEach
    public void setUp() {
        underTest = new BookServiceImpl(bookRepository, modelMapper);
    }

    @Test
    public void testFindAllShouldReturnAllBooks() {
        given(bookRepository.findAll()).willReturn(Collections.singletonList(BOOK));
        given(modelMapper.map(BOOK, BookDTO.class)).willReturn(BOOK_DTO);

        List<BookDTO> result = underTest.findAll();

        List<BookDTO> expected = Collections.singletonList(BOOK_DTO);

        assertEquals(expected, result);
    }

    @Test
    public void testFindByIdShouldReturnBookInOptionalIfItExists() {
        given(bookRepository.findById(BOOK_ID)).willReturn(Optional.of(BOOK));
        given(modelMapper.map(BOOK, BookDTO.class)).willReturn(BOOK_DTO);

        Optional<BookDTO> result = underTest.findById(BOOK_ID);

        Optional<BookDTO> expected = Optional.of(BOOK_DTO);

        assertEquals(expected, result);
    }

    @Test
    public void testFindByIdShouldReturnEmptyOptionalIfBookNotFound() {
        given(bookRepository.findById(BOOK_ID)).willReturn(Optional.empty());

        Optional<BookDTO> result = underTest.findById(BOOK_ID);

        Optional<BookDTO> expected = Optional.empty();

        assertEquals(expected, result);
    }

    @Test
    public void testUpdateShouldUpdateAndReturnBookIfItExists() {
        BookDTO bookDTOToUpdate = BookDTO.builder()
                .id(BOOK_ID)
                .build();
        Book storedBook = new Book();
        Optional<Book> storedBookOptional = Optional.of(storedBook);
        Book bookToUpdate = new Book();
        Book updatedBook = new Book();
        BookDTO updatedBookDTO = new BookDTO();

        given(bookRepository.findById(BOOK_ID)).willReturn(storedBookOptional);
        given(modelMapper.map(bookDTOToUpdate, Book.class)).willReturn(bookToUpdate);
        given(bookRepository.save(bookToUpdate)).willReturn(updatedBook);
        given(modelMapper.map(updatedBook, BookDTO.class)).willReturn(updatedBookDTO);

        BookDTO result = underTest.update(bookDTOToUpdate);

        assertEquals(updatedBookDTO, result);
    }

    @Test
    public void testUpdateShouldThrowExceptionIfBookNotFound() {
        BookDTO bookDTOToUpdate = BookDTO.builder()
                .id(BOOK_ID)
                .build();

        given(bookRepository.findById(BOOK_ID)).willReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            underTest.update(bookDTOToUpdate);
        });
    }

    @Test
    public void testDeleteShouldDeleteRequestedBook() {
        Book bookToDelete = new Book();
        bookToDelete.setId(BOOK_ID);
        Optional<Book> bookToDeleteOptional = Optional.of(bookToDelete);

        given(bookRepository.findById(BOOK_ID)).willReturn(bookToDeleteOptional);

        underTest.delete(BOOK_ID);

        verify(bookRepository).delete(bookToDelete);
    }

    @Test
    public void testDeleteShouldThrowExceptionIfBookNotFound() {

        given(bookRepository.findById(BOOK_ID)).willReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            underTest.delete(BOOK_ID);
        });
    }
}