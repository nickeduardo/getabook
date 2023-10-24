package br.com.nickeduardo.getabook.controller;

import br.com.nickeduardo.getabook.dto.PublisherDTO;
import br.com.nickeduardo.getabook.service.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publishers")
@Tag(name = "publishers", description = "This endpoint manages publishers")
public class PublisherController {

    @Autowired
    private PublisherService service;

    @PostMapping
    @Operation(summary = "Persists a new Publisher in database", tags = {"publishers"}, responses = {
            @ApiResponse(description = "Success!", responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PublisherDTO.class))
            })
    })
    public PublisherDTO create(@RequestBody PublisherDTO dto){
        return service.create(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a Publisher using the ID", tags = {"publishers"}, responses = {
            @ApiResponse(description = "Success!", responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema =
                        @Schema(implementation = PublisherDTO.class)
                    )
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = {@Content})
    })
    public PublisherDTO findById(@PathVariable("id") int id_publisher){
        PublisherDTO dto = service.findById(id_publisher);
        //..adding HATEOAS link
        buildEntityLink(dto);
        return dto;
    }

    @GetMapping
    public ResponseEntity<PagedModel<PublisherDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            PagedResourcesAssembler<PublisherDTO> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "publisherName"));

        Page<PublisherDTO> publishers = service.findAll(pageable);

        for (PublisherDTO publisher:publishers){
            buildEntityLink(publisher);
        }
        return new ResponseEntity(assembler.toModel(publishers), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<PagedModel<PublisherDTO>> findByName(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            PagedResourcesAssembler<PublisherDTO> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "publisherName"));

        Page<PublisherDTO> publishers = service.findByName(name, pageable);

        for (PublisherDTO publisher:publishers){
            buildEntityLink(publisher);
        }
        return new ResponseEntity(assembler.toModel(publishers), HttpStatus.OK);
    }

    @PutMapping
    public PublisherDTO update(@RequestBody PublisherDTO dto){
        return service.update(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id_publisher){
        PublisherDTO dto = service.findById(id_publisher);
        service.delete(dto);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    public void buildEntityLink(PublisherDTO publisher){
        //..self link
        publisher.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                this.getClass()
                        ).findById(publisher.getId_publisher())
                ).withSelfRel()
        );
    }

//    public void buildCollectionLink(CollectionModel<PublisherDTO> publishers){
//        publishers.add(
//                WebMvcLinkBuilder.linkTo(
//                        WebMvcLinkBuilder.methodOn(this.getClass()).findAll()
//                ).withRel(IanaLinkRelations.COLLECTION)
//        );
//    }

}
