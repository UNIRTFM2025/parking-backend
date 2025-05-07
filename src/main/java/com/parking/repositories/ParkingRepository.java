package com.parking.repositories;


import com.parking.entities.ParkingDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends MongoRepository<ParkingDocument, String> {

}
