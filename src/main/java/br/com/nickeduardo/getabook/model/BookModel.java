package br.com.nickeduardo.getabook.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "books")
public class BookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_book;

    @Column(name = "book_name", nullable = false, length = 50)
    private String bookName;

    @Column(name = "author_name", nullable = false, length = 50)
    private String authorName;

    @ManyToOne
    @JoinColumn(name="publisher")
    private PublisherModel publisher;

}
