package br.com.nickeduardo.getabook.service;

import br.com.nickeduardo.getabook.dto.PublisherDTO;
import br.com.nickeduardo.getabook.exception.ResourceNotFoundException;
import br.com.nickeduardo.getabook.mapper.CustomModelMapper;
import br.com.nickeduardo.getabook.model.PublisherModel;
import br.com.nickeduardo.getabook.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository repository;

    public PublisherDTO create(PublisherDTO dto){
        PublisherModel model = CustomModelMapper.parseObject(dto, PublisherModel.class);
        return CustomModelMapper.parseObject(repository.save(model), PublisherDTO.class);
    }

    public PublisherDTO findById(int id){
        PublisherModel model = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(null));
        return CustomModelMapper.parseObject(model, PublisherDTO.class);
    }

    public Page<PublisherDTO> findAll(Pageable pageable){
        var page = repository.findAll(pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, PublisherDTO.class));
    }

    public PublisherDTO update(PublisherDTO dto){
        PublisherModel model = repository.findById(dto.getId_publisher()).orElseThrow(
                () -> new ResourceNotFoundException(null));
        model = CustomModelMapper.parseObject(dto, PublisherModel.class);
        return CustomModelMapper.parseObject(repository.save(model), PublisherDTO.class);
    }

    public void delete(PublisherDTO dto){
        PublisherModel model = repository.findById(dto.getId_publisher()).orElseThrow(
                () -> new ResourceNotFoundException(null)
        );
        repository.delete(model);
    }

    public Page<PublisherDTO> findByName(String name, Pageable pageable){
        var page = repository.findByPublisherNameStartsWithIgnoreCase(name, pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, PublisherDTO.class));
    }

}
