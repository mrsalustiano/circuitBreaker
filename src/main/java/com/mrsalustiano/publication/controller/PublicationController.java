package com.mrsalustiano.publication.controller;

import com.mrsalustiano.publication.domain.Publication;
import com.mrsalustiano.publication.entity.PublicationEntity;
import com.mrsalustiano.publication.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publications")
@RequiredArgsConstructor
public class PublicationController {
    private final PublicationService service;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@RequestBody Publication publication) {
        service.insert(publication);
    }

    @GetMapping
    public List<Publication> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Publication findById(@PathVariable String id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody PublicationEntity publication){
        service.update(publication);
    }

}
