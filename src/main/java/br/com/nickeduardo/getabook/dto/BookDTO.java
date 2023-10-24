package br.com.nickeduardo.getabook.dto;

import br.com.nickeduardo.getabook.model.PublisherModel;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDTO extends RepresentationModel {

    private int id_book;

    @NotBlank
    @Size(min = 1, max = 50)
    private String bookName;

    @NotBlank
    @Size(min = 1, max = 50)
    private String authorName;

    @ManyToOne
    @JoinColumn(name="publisher")
    private PublisherModel publisher;

}
