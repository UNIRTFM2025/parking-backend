package com.parking.services;

import com.parking.entities.*;
import com.parking.repositories.SpaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpaceServiceTest {

    @Mock
    private SpaceRepository spaceRepository;

    @InjectMocks
    private SpaceService spaceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createParking_shouldSaveAndReturnParking() {
        SpaceDocument doc = new SpaceDocument();
        when(spaceRepository.save(doc)).thenReturn(doc);

        SpaceDocument result = spaceService.createParking(doc);

        assertEquals(doc, result);
        verify(spaceRepository).save(doc);
    }

    @Test
    void getAllParkings_shouldReturnList() {
        List<SpaceDocument> mockList = List.of(new SpaceDocument());
        when(spaceRepository.findAll()).thenReturn(mockList);

        List<SpaceDocument> result = spaceService.getAllParkings();

        assertEquals(1, result.size());
    }

    @Test
    void getParkingById_shouldReturnOptional() {
        SpaceDocument doc = new SpaceDocument();
        when(spaceRepository.findById("123")).thenReturn(Optional.of(doc));

        Optional<SpaceDocument> result = spaceService.getParkingById("123");

        assertTrue(result.isPresent());
        assertEquals(doc, result.get());
    }

    @Test
    void updateParking_shouldUpdateIfExists() {
        SpaceDocument existing = new SpaceDocument();
        SpaceDocument updated = new SpaceDocument();

        when(spaceRepository.findById("123")).thenReturn(Optional.of(existing));
        when(spaceRepository.save(any())).thenReturn(updated);

        Optional<SpaceDocument> result = spaceService.updateParking("123", updated);

        assertTrue(result.isPresent());
        verify(spaceRepository).save(updated);
        assertEquals("123", updated.getId());
    }

    @Test
    void updateParking_shouldReturnEmptyIfNotFound() {
        SpaceDocument updated = new SpaceDocument();
        when(spaceRepository.findById("123")).thenReturn(Optional.empty());

        Optional<SpaceDocument> result = spaceService.updateParking("123", updated);

        assertTrue(result.isEmpty());
        verify(spaceRepository, never()).save(any());
    }

    @Test
    void deleteParking_shouldDeleteIfExists() {
        SpaceDocument doc = new SpaceDocument();
        when(spaceRepository.findById("123")).thenReturn(Optional.of(doc));

        boolean result = spaceService.deleteParking("123");

        assertTrue(result);
        verify(spaceRepository).deleteById("123");
    }

    @Test
    void deleteParking_shouldReturnFalseIfNotFound() {
        when(spaceRepository.findById("123")).thenReturn(Optional.empty());

        boolean result = spaceService.deleteParking("123");

        assertFalse(result);
        verify(spaceRepository, never()).deleteById(anyString());
    }

    @Test
    void getParking_shouldReturnParkingIfFound() {
        Parking parking = new Parking();
        parking.setId(1);

        Site site = new Site();
        site.setPaking(List.of(parking));

        SpaceDocument doc = new SpaceDocument();
        doc.setSite(site);

        when(spaceRepository.findById("space1")).thenReturn(Optional.of(doc));

        Optional<Parking> result = spaceService.getParking("space1", 1);

        assertTrue(result.isPresent());
        assertEquals(parking, result.get());
    }

    @Test
    void getFloors_shouldReturnFloorsOfParking() {
        Floor floor = new Floor();
        floor.setNumber(1);

        Parking parking = new Parking();
        parking.setId(1);
        parking.setFloors(List.of(floor));

        Site site = new Site();
        site.setPaking(List.of(parking));

        SpaceDocument doc = new SpaceDocument();
        doc.setSite(site);

        when(spaceRepository.findById("space1")).thenReturn(Optional.of(doc));

        List<Floor> result = spaceService.getFloors("space1", 1);

        assertEquals(1, result.size());
        assertEquals(floor, result.get(0));
    }

    @Test
    void getFloor_shouldReturnSpecificFloor() {
        Floor floor = new Floor();
        floor.setNumber(2);

        Parking parking = new Parking();
        parking.setId(1);
        parking.setFloors(List.of(floor));

        Site site = new Site();
        site.setPaking(List.of(parking));

        SpaceDocument doc = new SpaceDocument();
        doc.setSite(site);

        when(spaceRepository.findById("space1")).thenReturn(Optional.of(doc));

        Optional<Floor> result = spaceService.getFloor("space1", 1, 2);

        assertTrue(result.isPresent());
        assertEquals(2, result.get().getNumber());
    }

    @Test
    void updateSlot_shouldUpdateStatusIfFound() {
        Slot slot = new Slot();
        slot.setId("A1");
        slot.setStatus(false);

        Floor floor = new Floor();
        floor.setNumber(1);
        floor.setSlots(List.of(slot));

        Parking parking = new Parking();
        parking.setId(2);
        parking.setFloors(List.of(floor));

        Site site = new Site();
        site.setPaking(List.of(parking));

        SpaceDocument space = new SpaceDocument();
        space.setSite(site);

        when(spaceRepository.findById("space1")).thenReturn(Optional.of(space));
        when(spaceRepository.save(any())).thenReturn(space);

        Optional<Slot> result = spaceService.updateSlot("space1", 2, 1, "A1", true);

        assertTrue(result.isPresent());
        assertTrue(result.get().isStatus());
    }
}
