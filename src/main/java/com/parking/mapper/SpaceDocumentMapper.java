package com.parking.mapper;

import com.parking.dto.SpaceDocumentDTO;
import com.parking.entities.SpaceDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpaceDocumentMapper {

    SpaceDocument toEntity(SpaceDocumentDTO spaceDocumentDTO);
    SpaceDocumentDTO toDTO(SpaceDocument spaceDocument);
}
