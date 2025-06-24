package com.parking.controller;

import com.parking.entities.Floor;
import com.parking.entities.Parking;
import com.parking.entities.Slot;
import com.parking.entities.SpaceDocument;
import com.parking.services.SpaceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ParkingController.class)
class ParkingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceService spaceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateParking() throws Exception {
        SpaceDocument doc = new SpaceDocument();
        doc.setId("123");

        Mockito.when(spaceService.createParking(any(SpaceDocument.class))).thenReturn(doc);

        mockMvc.perform(post("/spaces")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void testGetAllParkings() throws Exception {
        SpaceDocument doc = new SpaceDocument();
        doc.setId("123");

        Mockito.when(spaceService.getAllParkings()).thenReturn(List.of(doc));

        mockMvc.perform(get("/spaces"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("123"));
    }

    @Test
    void testGetParkingById_found() throws Exception {
        SpaceDocument doc = new SpaceDocument();
        doc.setId("123");

        Mockito.when(spaceService.getParkingById("123")).thenReturn(Optional.of(doc));

        mockMvc.perform(get("/spaces/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void testGetParkingById_notFound() throws Exception {
        Mockito.when(spaceService.getParkingById("404")).thenReturn(Optional.empty());

        mockMvc.perform(get("/spaces/404"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateParking_found() throws Exception {
        SpaceDocument updated = new SpaceDocument();
        updated.setId("123");

        Mockito.when(spaceService.updateParking(eq("123"), any(SpaceDocument.class)))
                .thenReturn(Optional.of(updated));

        mockMvc.perform(put("/spaces/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void testUpdateParking_notFound() throws Exception {
        SpaceDocument doc = new SpaceDocument();
        doc.setId("123");

        Mockito.when(spaceService.updateParking(eq("123"), any(SpaceDocument.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/spaces/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doc)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteParking_found() throws Exception {
        Mockito.when(spaceService.deleteParking("123")).thenReturn(true);

        mockMvc.perform(delete("/spaces/123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteParking_notFound() throws Exception {
        Mockito.when(spaceService.deleteParking("404")).thenReturn(false);

        mockMvc.perform(delete("/spaces/404"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetParkingNested() throws Exception {
        Parking parking = new Parking();
        parking.setZone("Test Parking");

        Mockito.when(spaceService.getParking("space1", 1)).thenReturn(Optional.of(parking));

        mockMvc.perform(get("/spaces/space1/parkings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zone").value("Test Parking"));
    }

    @Test
    void testGetFloors() throws Exception {
        Floor floor = new Floor();
        floor.setNumber(1);

        Mockito.when(spaceService.getFloors("space1", 1)).thenReturn(List.of(floor));

        mockMvc.perform(get("/spaces/space1/parkings/1/floors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].number").value(1));
    }

    @Test
    void testUpdateSlot_found() throws Exception {
        Slot slot = new Slot();
        slot.setId("A1");
        slot.setStatus(true);

        Mockito.when(spaceService.updateSlot("space1", 1, 2, "A1", true))
                .thenReturn(Optional.of(slot));

        mockMvc.perform(patch("/spaces/space1/parkings/1/floors/2/slots/A1?status=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("A1"))
                .andExpect(jsonPath("$.status").value(true));
    }
}