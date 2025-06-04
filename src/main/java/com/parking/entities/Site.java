package com.parking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Site {
    private String name;
    private String rating;
    private boolean covered;
    private Location location;
    private Address address;
    private List<Parking> paking;
}
