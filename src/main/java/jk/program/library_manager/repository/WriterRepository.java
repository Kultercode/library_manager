package jk.program.library_manager.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import jk.program.library_manager.entity.Book;
import jk.program.library_manager.entity.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WriterRepository extends JpaRepository<Writer, Long> {

    @Query(value = "SELECT * FROM writers WHERE name = ?1", nativeQuery = true)
    List<Writer> findByName(String name);

    @Query(value = "SELECT * FROM writers WHERE birth_date = ?1", nativeQuery = true)
    List<Writer> findByBirthDate(Date birthDate);
}
