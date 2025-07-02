package com.parking.mapper;

import com.parking.dto.SiteDTO;
import com.parking.entities.Site;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SiteMapper {

    SiteDTO toDTO(Site site);
    Site toEntity(SiteDTO siteDTO);
}
