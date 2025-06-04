package com.parking.repositories;


import com.parking.entities.SpaceDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends MongoRepository<SpaceDocument, String> {

}
