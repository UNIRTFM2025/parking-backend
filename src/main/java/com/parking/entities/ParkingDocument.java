package com.parking.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "establishments")
public class ParkingDocument {
    @Id
    private String id;
    private Site site;
}
