package br.com.nickeduardo.getabook.service;

import br.com.nickeduardo.getabook.dto.ReaderDTO;
import br.com.nickeduardo.getabook.exception.ResourceNotFoundException;
import br.com.nickeduardo.getabook.mapper.CustomModelMapper;
import br.com.nickeduardo.getabook.model.ReaderModel;
import br.com.nickeduardo.getabook.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReaderService {

    @Autowired
    private ReaderRepository repository;

    public ReaderDTO create(ReaderDTO dto){
        ReaderModel model = CustomModelMapper.parseObject(dto, ReaderModel.class);
        return CustomModelMapper.parseObject(repository.save(model), ReaderDTO.class);
    }

    public ReaderDTO findById(int id){
        ReaderModel model = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(null));
        return CustomModelMapper.parseObject(model, ReaderDTO.class);
    }

    public Page<ReaderDTO> findAll(Pageable pageable){
        var page = repository.findAll(pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, ReaderDTO.class));
    }

    public ReaderDTO update(ReaderDTO dto){
        ReaderModel model = repository.findById(dto.getId_reader()).orElseThrow(
                () -> new ResourceNotFoundException(null));
        model = CustomModelMapper.parseObject(dto, ReaderModel.class);
        return CustomModelMapper.parseObject(repository.save(model), ReaderDTO.class);
    }

    public void delete(ReaderDTO dto){
        ReaderModel model = repository.findById(dto.getId_reader()).orElseThrow(
                () -> new ResourceNotFoundException(null)
        );
        repository.delete(model);
    }

    public Page<ReaderDTO> findByName(String name, Pageable pageable){
        var page = repository.findByFirstNameStartsWithIgnoreCase(name, pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, ReaderDTO.class));
    }

}
