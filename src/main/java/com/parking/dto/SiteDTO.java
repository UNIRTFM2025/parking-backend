package com.parking.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.parking.entities.Address;
import com.parking.entities.Location;
import com.parking.entities.Parking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, allowSetters = true)
public class SiteDTO {

    private String name;
    private String rating;
    private boolean covered;
    private Location location;
    private Address address;
    private Integer capacity;
    private Integer available;
    private List<ParkingDTO> paking;
}
