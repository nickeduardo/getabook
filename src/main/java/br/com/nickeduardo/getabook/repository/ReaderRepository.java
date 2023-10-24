package br.com.nickeduardo.getabook.repository;

import br.com.nickeduardo.getabook.model.ReaderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends JpaRepository<ReaderModel, Integer> {

    public Page<ReaderModel> findAll(Pageable pageable);

    public Page<ReaderModel> findByFirstNameStartsWithIgnoreCase(String name, Pageable pageable);

}
