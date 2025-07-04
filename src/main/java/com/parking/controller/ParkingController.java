package com.parking.controller;

import com.parking.dto.FloorDTO;
import com.parking.dto.SpaceDocumentDTO;
import com.parking.entities.Floor;
import com.parking.entities.Parking;
import com.parking.entities.Slot;
import com.parking.entities.SpaceDocument;
import com.parking.services.SpaceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spaces")
@Tag(name = "Parking API", description = "Operaciones CRUD para parqueaderos")
public class ParkingController {

    @Autowired
    private SpaceService spaceService;


    @PostMapping
    public SpaceDocument createParking(@RequestBody SpaceDocument parking) {
        return spaceService.createParking(parking);
    }

    @GetMapping
    public List<SpaceDocumentDTO> getAllParkings() {
        return spaceService.getAllParkings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaceDocumentDTO> getParkingById(@PathVariable String id) {
        return spaceService.getSpaceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpaceDocument> updateParking(@PathVariable String id, @RequestBody SpaceDocument updatedParking) {
        return spaceService.updateParking(id, updatedParking)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParking(@PathVariable String id) {
        boolean deleted = spaceService.deleteParking(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/parkings/{parkingId}")
    public ResponseEntity<Parking> getParking(@PathVariable String id, @PathVariable int parkingId) {
        return spaceService.getParking(id, parkingId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/parkings/{parkingId}/floors")
    public ResponseEntity<List<Floor>> getFloors(@PathVariable String id, @PathVariable int parkingId) {
        return ResponseEntity.ok(spaceService.getFloors(id, parkingId));
    }

    @GetMapping("/{id}/parkings/{parkingId}/floors/{floorNumber}")
    public ResponseEntity<FloorDTO> getFloor(@PathVariable String id, @PathVariable int parkingId, @PathVariable int floorNumber) {
        return spaceService.getFloor(id, parkingId, floorNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/parkings/{parkingId}/floors/{floorNumber}/slots/{slotId}")
    public ResponseEntity<Slot> updateSlot(@PathVariable String id, @PathVariable int parkingId,
                                           @PathVariable int floorNumber, @PathVariable String slotId,
                                           @RequestParam boolean status) {
        return spaceService.updateSlot(id, parkingId, floorNumber, slotId, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

