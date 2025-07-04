package com.parking.mapper;

import com.parking.dto.ParkingDTO;
import com.parking.entities.Parking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParkingMapper {
    Parking toEntity(ParkingDTO parkingDTO);
    ParkingDTO toDTO(Parking parking);
}
