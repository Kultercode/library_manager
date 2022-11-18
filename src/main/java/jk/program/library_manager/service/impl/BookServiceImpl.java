package jk.program.library_manager.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jk.program.library_manager.dto.BookDTO;
import jk.program.library_manager.entity.Book;
import jk.program.library_manager.exception.BookNotFoundException;
import jk.program.library_manager.exception.WriterNotFoundException;
import jk.program.library_manager.repository.WriterRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import jk.program.library_manager.repository.BookRepository;
import jk.program.library_manager.service.BookService;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;
    private final WriterRepository writerRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<BookDTO> findAll() {
        List<Book> bookList = bookRepository.findAll();
        LOGGER.info("Successfully completed books findAll request.");

        return bookList.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO create(BookDTO bookDTO) {
        Long writerId = bookDTO.getWriterId();

        if(writerRepository.findById(writerId).isEmpty()) {
            LOGGER.error("Writer not found with writerId = {}!", writerId);
            throw new WriterNotFoundException("Writer not found with id = " + writerId);
        }

        Book bookToSave = modelMapper.map(bookDTO, Book.class);
        bookToSave.setId(null);
        Book savedBook = bookRepository.save(bookToSave);
        return modelMapper.map(savedBook, BookDTO.class);
    }

    @Override
    public Optional<BookDTO> findById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.map(book -> modelMapper.map(book, BookDTO.class));
    }

    @Override
    public List<BookDTO> findByTitle(String title) {
        List<Book> listBook = bookRepository.findByTitle(title);

        return listBook.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findByReleaseDate(Date title) {
        List<Book> listBook = bookRepository.findByReleaseDate(title);

        return listBook.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findByTitleAndReleaseDate(String title, Date releaseDate) {
        List<Book> listBook = bookRepository.findByTitleAndReleaseDate(title, releaseDate);

        return listBook.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO update(BookDTO bookDTO) {
        Long id = bookDTO.getId();
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isEmpty()) {
            LOGGER.error("Book not found with id = {}!", id);
            throw new BookNotFoundException("Book not found with id = " + id);
        }

        Long writerId = bookDTO.getWriterId();
        if(writerRepository.findById(writerId).isEmpty()) {
            LOGGER.error("Writer not found with authorId = {}!", writerId);
            throw new WriterNotFoundException("Writer not found with id = " + writerId);
        }

        Book bookToUpdate = modelMapper.map(bookDTO, Book.class);
        Book savedBook = bookRepository.save(bookToUpdate);
        return modelMapper.map(savedBook, BookDTO.class);
    }

    @Override
    public void delete(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            Book bookToDelete = optionalBook.get();
            bookRepository.delete(bookToDelete);
        } else {
            LOGGER.error("Book not found with id = {}!", id);
            throw new BookNotFoundException("Book not found with id = " + id);
        }
    }
}
