package com.parking.services;

import com.parking.entities.Floor;
import com.parking.entities.Parking;
import com.parking.entities.SpaceDocument;
import com.parking.entities.Slot;
import com.parking.repositories.SpaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SpaceService {

    private SpaceRepository spaceRepository;

    public SpaceDocument createParking(SpaceDocument parking) {
        return spaceRepository.save(parking);
    }

    public List<SpaceDocument> getAllParkings() {
        return spaceRepository.findAll();
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

    public Optional<SpaceDocument> getSpaceById(String id) {
        return spaceRepository.findById(id);
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

    public Optional<Floor> getFloor(String spaceId, int parkingId, int floorNumber) {
        return getFloors(spaceId, parkingId).stream()
                .filter(f -> f.getNumber() == floorNumber)
                .findFirst();
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