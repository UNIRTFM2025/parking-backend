package com.parking.services;

import com.parking.dto.FloorDTO;
import com.parking.dto.ParkingDTO;
import com.parking.dto.SpaceDocumentDTO;
import com.parking.entities.Floor;
import com.parking.entities.Parking;
import com.parking.entities.SpaceDocument;
import com.parking.entities.Slot;
import com.parking.mapper.FloorMapper;
import com.parking.mapper.ParkingMapper;
import com.parking.mapper.SpaceDocumentMapper;
import com.parking.repositories.SpaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SpaceService {

    private SpaceRepository spaceRepository;
    private SpaceDocumentMapper spaceDocumentMapper;
    private ParkingMapper parkingMapper;
    private FloorMapper floorMapper;

    public SpaceDocument createParking(SpaceDocument parking) {
        return spaceRepository.save(parking);
    }

    public List<SpaceDocumentDTO> getAllParkings() {
        return spaceRepository.findAll().stream().map(this::getSpaceDocumentDTO).collect(Collectors.toList());
    }

    private SpaceDocumentDTO getSpaceDocumentDTO(SpaceDocument space) {
        SpaceDocumentDTO dto = spaceDocumentMapper.toDTO(space);
        dto.getSite().setCapacity(0);
        dto.getSite().setAvailable(0);

        space.getSite().getPaking().forEach(parking -> {
            parking.getFloors().forEach(floor -> {
                dto.getSite().setCapacity(dto.getSite().getCapacity() + floor.getSlots().size());
                long availableSlots = floor.getSlots().stream()
                        .filter(Slot::isStatus) // Si es true = libre
                        .count();
                dto.getSite().setAvailable(dto.getSite().getAvailable() + (int) availableSlots);
            });
        });

        dto.getSite().setPaking(null); // Limpias si no necesitas los datos del parking
        return dto;
    }

    public Optional<SpaceDocument> getParkingById(String id) {
        return spaceRepository.findById(id);
    }

    public Optional<SpaceDocument> updateParking(String id, SpaceDocument updatedParking) {
        return spaceRepository.findById(id).map(existing -> {
            updatedParking.setId(id);
            return spaceRepository.save(updatedParking);
        });
    }

    public boolean deleteParking(String id) {
        return spaceRepository.findById(id).map(parking -> {
            spaceRepository.deleteById(id);
            return true;
        }).orElse(false);
    }

    public Optional<SpaceDocumentDTO> getSpaceById(String id) {
        return spaceRepository.findById(id).map(spaceDocument -> {
            SpaceDocumentDTO dto = spaceDocumentMapper.toDTO(spaceDocument);
            dto.getSite().setCapacity(null);
            dto.getSite().setAvailable(null);
            List<ParkingDTO> parkingDTOList = spaceDocument.getSite().getPaking().stream().map(this::getParkingDTO).toList();
            dto.getSite().setPaking(parkingDTOList);
            return dto;
        });
    }

    private ParkingDTO getParkingDTO(Parking parking) {
        ParkingDTO parkingDTO = parkingMapper.toDTO(parking);
        parkingDTO.setCapacity(0);
        parkingDTO.setAvailable(0);
        List<FloorDTO> floorDTOS = parking.getFloors().stream().map(floor -> getFloorDTO(floor, parkingDTO)).toList();
        parkingDTO.setFloors(floorDTOS);
        return parkingDTO;
    }

    private FloorDTO getFloorDTO(Floor floor, ParkingDTO parkingDTO) {
        FloorDTO floorDTO = floorMapper.toDto(floor);
        parkingDTO.setCapacity(parkingDTO.getCapacity() + floor.getSlots().size());
        long availableSlots = floor.getSlots().stream()
                .filter(Slot::isStatus) // Si es true = libre
                .count();
        parkingDTO.setAvailable(parkingDTO.getAvailable() + (int) availableSlots);
        floorDTO.setSlots(null);
        return floorDTO;
    }

    public Optional<Parking> getParking(String spaceId, int parkingId) {
        return spaceRepository.findById(spaceId)
                .flatMap(space -> space.getSite().getPaking().stream()
                        .filter(p -> p.getId() == parkingId)
                        .findFirst());
    }

    public List<Floor> getFloors(String spaceId, int parkingId) {
        return getParking(spaceId, parkingId)
                .map(Parking::getFloors)
                .orElse(Collections.emptyList());
    }

    public Optional<FloorDTO> getFloor(String spaceId, int parkingId, int floorNumber) {
        return getFloors(spaceId, parkingId).stream()
                .filter(f -> f.getNumber() == floorNumber)
                .findFirst()
                .map(floor -> {
                    FloorDTO floorDTO = floorMapper.toDto(floor);
                    floorDTO.setCapacity(0);
                    floorDTO.setAvailable(0);
                    floorDTO.setCapacity(floorDTO.getCapacity() + floor.getSlots().size());
                    long availableSlots = floor.getSlots().stream()
                            .filter(Slot::isStatus) // Si es true = libre
                            .count();
                    floorDTO.setAvailable(floorDTO.getAvailable() + (int) availableSlots);
                    return floorDTO;
                });
    }

    public Optional<Slot> updateSlot(String spaceId, int parkingId, int floorNumber, String slotId, boolean newStatus) {
        Optional<SpaceDocument> optionalSpace = spaceRepository.findById(spaceId);
        if (optionalSpace.isEmpty()) return Optional.empty();

        SpaceDocument space = optionalSpace.get();
        for (Parking p : space.getSite().getPaking()) {
            if (p.getId() == parkingId) {
                for (Floor f : p.getFloors()) {
                    if (f.getNumber() == floorNumber) {
                        for (Slot s : f.getSlots()) {
                            if (s.getId().equals(slotId)) {
                                s.setStatus(newStatus);
                                spaceRepository.save(space);
                                return Optional.of(s);
                            }
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
}