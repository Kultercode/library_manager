package jk.program.library_manager.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import jk.program.library_manager.dto.BookDTO;

public interface BookService {

    List<BookDTO> findAll();

    BookDTO create(BookDTO bookDTO);

    Optional<BookDTO> findById(Long id);

    List<BookDTO> findByTitle(String title);

    List<BookDTO> findByReleaseDate(Date releaseDate);

    BookDTO update(BookDTO bookDTO);

    void delete(Long id);
}
