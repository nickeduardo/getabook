package br.com.nickeduardo.getabook.repository;

import br.com.nickeduardo.getabook.model.PublisherModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<PublisherModel, Integer> {

    public Page<PublisherModel> findAll(Pageable pageable);

    public Page<PublisherModel> findByPublisherNameStartsWithIgnoreCase(String name, Pageable pageable);

}
