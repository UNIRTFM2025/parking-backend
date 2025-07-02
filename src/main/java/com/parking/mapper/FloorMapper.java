package com.parking.mapper;

import com.parking.dto.FloorDTO;
import com.parking.entities.Floor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FloorMapper {

    FloorDTO toDto (Floor floor);
    Floor toEntity (FloorDTO floorDTO);
}
