package com.mrsalustiano.publication.repository;

import com.mrsalustiano.publication.entity.PublicationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends MongoRepository<PublicationEntity, String> {
}
