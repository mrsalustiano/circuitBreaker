package com.mrsalustiano.publication.mapper;

import com.mrsalustiano.publication.domain.Publication;
import com.mrsalustiano.publication.domain.PublicationRequest;
import com.mrsalustiano.publication.entity.PublicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PublicationMapper {

    PublicationEntity toEntity(Publication publication);

    @Mapping(target = "id", source = "id")
    Publication toDomain(PublicationEntity publicationEntity);

    Publication toDomain(PublicationRequest publicationRequest);
}
