package jk.program.library_manager.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jk.program.library_manager.dto.BookDTO;
import jk.program.library_manager.dto.WriterDTO;
import jk.program.library_manager.entity.Book;
import jk.program.library_manager.entity.Writer;
import jk.program.library_manager.exception.WriterNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import jk.program.library_manager.repository.WriterRepository;
import jk.program.library_manager.service.WriterService;

@Service
@AllArgsConstructor
public class WriterServiceImpl implements WriterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriterServiceImpl.class);

    private final WriterRepository writerRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<WriterDTO> findAll() {
        List<Writer> writerList = writerRepository.findAll();
        LOGGER.info("Successfully completed writers findAll request.");

        return writerList.stream()
                .map(writer -> modelMapper.map(writer, WriterDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public WriterDTO create(WriterDTO writerDTO) {
        Writer writerToSave = modelMapper.map(writerDTO, Writer.class);
        writerToSave.setId(null);
        Writer savedWriter = writerRepository.save(writerToSave);
        return modelMapper.map(savedWriter, WriterDTO.class);
    }

    @Override
    public Optional<WriterDTO> findById(Long id) {
        Optional<Writer> optionalWriter = writerRepository.findById(id);
        return optionalWriter.map(writer -> modelMapper.map(writer, WriterDTO.class));
    }

    @Override
    public List<WriterDTO> findByName(String name) {
        List<Writer> listWriter = writerRepository.findByName(name);

        return listWriter.stream()
                .map(writer -> modelMapper.map(writer, WriterDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<WriterDTO> findByBirthDate(Date birthDate) {
        List<Writer> listWriter = writerRepository.findByBirthDate(birthDate);

        return listWriter.stream()
                .map(writer -> modelMapper.map(writer, WriterDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public WriterDTO update(WriterDTO writerDTO) {
        Long id = writerDTO.getId();
        Optional<Writer> optionalWriter = writerRepository.findById(id);

        if (optionalWriter.isEmpty()) {
            LOGGER.error("Writer not found with id = {}!", id);
            throw new WriterNotFoundException("Writer not found with id = " + id);
        }

        Writer writerToUpdate = modelMapper.map(writerDTO, Writer.class);
        Writer savedWriter = writerRepository.save(writerToUpdate);
        return modelMapper.map(savedWriter, WriterDTO.class);
    }

    @Override
    public void delete(Long id) {
        Optional<Writer> optionalWriter = writerRepository.findById(id);

        if (optionalWriter.isPresent()) {
            Writer writerToDelete = optionalWriter.get();
            writerRepository.delete(writerToDelete);
        } else {
            LOGGER.error("Book not found with id = {}!", id);
            throw new WriterNotFoundException("Writer not found with id = " + id);
        }
    }
}
