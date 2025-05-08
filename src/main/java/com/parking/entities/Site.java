package com.parking.entities;

import lombok.Data;

import java.util.List;

@Data
public class Site {
    private String name;
    private String rating;
    private boolean covered;
    private Location location;
    private Address address;
    private List<Floor> floors;
}
