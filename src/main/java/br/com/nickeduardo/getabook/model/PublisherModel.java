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
@Table(name = "publishers")
public class PublisherModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_publisher;

    @Column(name = "publisher_name", nullable = false, length = 50)
    private String publisherName;

    @Column(nullable = false, length = 20)
    private String contact;

    @Column(name = "establishment_year", nullable = false)
    private int establishmentYear;

}
