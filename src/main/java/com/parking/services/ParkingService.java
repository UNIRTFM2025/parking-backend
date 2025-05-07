package com.parking.services;

import com.parking.entities.ParkingDocument;
import com.parking.repositories.ParkingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParkingService {

    private ParkingRepository parkingRepository;

    public ParkingDocument createParking(ParkingDocument parking) {
        return parkingRepository.save(parking);
    }

    public List<ParkingDocument> getAllParkings() {
        return parkingRepository.findAll();
    }

    public Optional<ParkingDocument> getParkingById(String id) {
        return parkingRepository.findById(id);
    }

    public Optional<ParkingDocument> updateParking(String id, ParkingDocument updatedParking) {
        return parkingRepository.findById(id).map(existing -> {
            updatedParking.setId(id);
            return parkingRepository.save(updatedParking);
        });
    }

    public boolean deleteParking(String id) {
        return parkingRepository.findById(id).map(parking -> {
            parkingRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}