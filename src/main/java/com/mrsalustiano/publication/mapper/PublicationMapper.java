package com.mrsalustiano.publication.mapper;

import com.mrsalustiano.publication.domain.Publication;
import com.mrsalustiano.publication.entity.PublicationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface PublicationMapper {

    PublicationEntity toEntity(Publication publication);

    Publication toDomain(PublicationEntity publicationEntity);
}
