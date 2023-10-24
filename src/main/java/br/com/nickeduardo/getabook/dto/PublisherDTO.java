package br.com.nickeduardo.getabook.dto;

import br.com.nickeduardo.getabook.model.ReaderModel;
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
public class PublisherDTO extends RepresentationModel {

    private int id_publisher;

    @NotBlank
    @Size(min = 1, max = 50)
    private String publisherName;

    @NotBlank
    @Size(min = 1, max = 20)
    private String Contact;

    @NotBlank
    @Size(min = 5, max = 50)
    private int establishmentYear;

}
