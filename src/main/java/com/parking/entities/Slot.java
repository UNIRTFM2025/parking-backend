package com.parking.entities;

import lombok.Data;

@Data
public class Slot {
    private String id;
    private boolean status;
    private String type;

}