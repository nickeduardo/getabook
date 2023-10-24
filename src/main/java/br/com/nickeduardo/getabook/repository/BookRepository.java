package br.com.nickeduardo.getabook.repository;

import br.com.nickeduardo.getabook.model.BookModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Integer> {

    public Page<BookModel> findAll(Pageable pageable);

    public Page<BookModel> findByBookNameStartsWithIgnoreCase(String name, Pageable pageable);

}
