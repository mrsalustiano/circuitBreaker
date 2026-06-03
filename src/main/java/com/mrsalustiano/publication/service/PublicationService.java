package com.mrsalustiano.publication.service;

import com.mrsalustiano.publication.domain.Publication;
import com.mrsalustiano.publication.entity.PublicationEntity;
import com.mrsalustiano.publication.mapper.PublicationMapper;
import com.mrsalustiano.publication.repository.PublicationRepository;
import com.mrsalustiano.publication.resilience.CommentsResilienceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationService {

    private final PublicationRepository repository;
    private final PublicationMapper mapper;
    private final CommentsResilienceClient commentsClient;

    public void insert(Publication publication) {
        repository.insert(mapper.toEntity(publication));
    }

    public List<Publication> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    public Publication findById(String id) {
        var publication = repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Publication not found with id: " + id
                ));
        publication.setComments(commentsClient.getComments(id));
        return publication;
    }

    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Publication not found with id: " + id
            );
        }
        repository.deleteById(id);
    }

    public void update(PublicationEntity publication) {
        repository.findById(publication.getId())
                .map(existing -> repository.save(publication))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Publication not found with id: " + publication.getId()
                ));
    }
}
