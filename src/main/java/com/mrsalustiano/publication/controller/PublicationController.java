package com.mrsalustiano.publication.controller;

import com.mrsalustiano.publication.domain.Publication;
import com.mrsalustiano.publication.domain.PublicationRequest;
import com.mrsalustiano.publication.entity.PublicationEntity;
import com.mrsalustiano.publication.mapper.PublicationMapper;
import com.mrsalustiano.publication.service.PublicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publications")
@RequiredArgsConstructor
public class PublicationController {
    private final PublicationService service;
    private final PublicationMapper mapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@Valid @RequestBody PublicationRequest publication) {
        var pub = mapper.toDomain(publication);
        service.insert(pub);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Publication> findAll() {
        var retorno = service.findAll();
        return retorno;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Publication findById(@PathVariable(value = "id") String id) {
        return service.findById(id);
    }



}
