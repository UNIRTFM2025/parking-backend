package com.parking.entities;


import lombok.Data;

import java.util.List;

@Data
public class Floor {
    private int number;
    private List<Slot> slots;

}