package jk.program.library_manager.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import jk.program.library_manager.dto.BookDTO;
import jk.program.library_manager.dto.WriterDTO;

public interface WriterService {

    List<WriterDTO> findAll();

    WriterDTO create(WriterDTO writerDTO);

    Optional<WriterDTO> findById(Long id);

    List<WriterDTO> findByName(String name);

    List<WriterDTO> findByBirthDate(Date birthDate);

    WriterDTO update(WriterDTO writerDTO);

    void delete(Long id);
}
