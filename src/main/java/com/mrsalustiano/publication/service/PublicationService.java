package com.mrsalustiano.publication.service;

import com.mrsalustiano.publication.domain.Publication;
import com.mrsalustiano.publication.entity.PublicationEntity;
import com.mrsalustiano.publication.mapper.PublicationMapper;
import com.mrsalustiano.publication.repository.PublicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class PublicationService {

    @Autowired
    private PublicationRepository repository;

    @Autowired
    private PublicationMapper mapper;

    public PublicationService(PublicationRepository repository, PublicationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void insert(Publication publication) {
        repository.insert(mapper.toEntity(publication));
    }

    public List<Publication> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    public Publication findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publication not found with id: " + id));
    }

    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Publication not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public void update(PublicationEntity publication) {
        repository.findById(publication.getId())
                .map(existingPublication -> repository.save(publication))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publication not found with id: " + publication.getId()));
    }

}
