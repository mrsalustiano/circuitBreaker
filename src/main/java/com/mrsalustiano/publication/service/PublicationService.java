package com.mrsalustiano.publication.service;

import com.mrsalustiano.publication.domain.Publication;
import com.mrsalustiano.publication.mapper.PublicationMapper;
import com.mrsalustiano.publication.repository.PublicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicationService {

    private final PublicationRepository repository;
    private final PublicationMapper mapper;

    public void insert(Publication publication) {
        repository.insert(mapper.toEntity(publication));
    }

    public List<Publication> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

}
