package br.com.nickeduardo.getabook.controller;

import br.com.nickeduardo.getabook.dto.ReaderDTO;
import br.com.nickeduardo.getabook.service.ReaderService;
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
@RequestMapping("/api/readers")
@Tag(name = "Readers", description = "This endpoint manages Readers")
public class ReaderController {

    @Autowired
    private ReaderService service;

    @PostMapping
    @Operation(summary = "Persists a new Reader in database", tags = {"Readers"}, responses = {
            @ApiResponse(description = "Success!", responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReaderDTO.class))
            })
    })
    public ReaderDTO create(@RequestBody ReaderDTO dto){
        return service.create(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a Reader using the ID", tags = {"Readers"}, responses = {
            @ApiResponse(description = "Success!", responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema =
                        @Schema(implementation = ReaderDTO.class)
                    )
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = {@Content})
    })
    public ReaderDTO findById(@PathVariable("id") int id){
        ReaderDTO dto = service.findById(id);
        //..adding HATEOAS link
        buildEntityLink(dto);
        return dto;
    }

    @GetMapping
    public ResponseEntity<PagedModel<ReaderDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            PagedResourcesAssembler<ReaderDTO> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));

        Page<ReaderDTO> readers = service.findAll(pageable);

        for (ReaderDTO reader:readers){
            buildEntityLink(reader);
        }
        return new ResponseEntity(assembler.toModel(readers), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<PagedModel<ReaderDTO>> findByName(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            PagedResourcesAssembler<ReaderDTO> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));

        Page<ReaderDTO> readers = service.findByName(name, pageable);

        for (ReaderDTO reader:readers){
            buildEntityLink(reader);
        }
        return new ResponseEntity(assembler.toModel(readers), HttpStatus.OK);
    }

    @PutMapping
    public ReaderDTO update(@RequestBody ReaderDTO dto){
        return service.update(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        ReaderDTO dto = service.findById(id);
        service.delete(dto);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    public void buildEntityLink(ReaderDTO reader){
        //..self link
        reader.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                this.getClass()
                        ).findById(reader.getId_reader())
                ).withSelfRel()
        );
    }

//    public void buildCollectionLink(CollectionModel<ReaderDTO> readers){
//        readers.add(
//                WebMvcLinkBuilder.linkTo(
//                        WebMvcLinkBuilder.methodOn(this.getClass()).findAll()
//                ).withRel(IanaLinkRelations.COLLECTION)
//        );
//    }


}
