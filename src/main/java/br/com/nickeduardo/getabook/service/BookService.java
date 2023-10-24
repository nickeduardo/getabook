package br.com.nickeduardo.getabook.service;

import br.com.nickeduardo.getabook.dto.BookDTO;
import br.com.nickeduardo.getabook.exception.ResourceNotFoundException;
import br.com.nickeduardo.getabook.mapper.CustomModelMapper;
import br.com.nickeduardo.getabook.model.BookModel;
import br.com.nickeduardo.getabook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public BookDTO create(BookDTO dto){
        BookModel model = CustomModelMapper.parseObject(dto, BookModel.class);
        return CustomModelMapper.parseObject(repository.save(model), BookDTO.class);
    }

    public BookDTO findById(int id){
        BookModel model = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(null));
        return CustomModelMapper.parseObject(model, BookDTO.class);
    }

    public Page<BookDTO> findAll(Pageable pageable){
        var page = repository.findAll(pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, BookDTO.class));
    }

    public BookDTO update(BookDTO dto){
        BookModel model = repository.findById(dto.getId_book()).orElseThrow(
                () -> new ResourceNotFoundException(null));
        model = CustomModelMapper.parseObject(dto, BookModel.class);
        return CustomModelMapper.parseObject(repository.save(model), BookDTO.class);
    }

    public void delete(BookDTO dto){
        BookModel model = repository.findById(dto.getId_book()).orElseThrow(
                () -> new ResourceNotFoundException(null)
        );
        repository.delete(model);
    }

    public Page<BookDTO> findByName(String name, Pageable pageable){
        var page = repository.findByBookNameStartsWithIgnoreCase(name, pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, BookDTO.class));
    }

}
