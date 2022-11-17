package jk.program.library_manager.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import jk.program.library_manager.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM books WHERE title = ?1", nativeQuery = true)
    List<Book> findByTitle(String title);

    @Query(value = "SELECT * FROM books WHERE release_date = ?1", nativeQuery = true)
    List<Book> findByReleaseDate(Date releaseDate);
}
