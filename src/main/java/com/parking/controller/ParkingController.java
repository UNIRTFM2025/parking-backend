package com.parking.controller;

import com.parking.entities.ParkingDocument;
import com.parking.services.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parkings")
@Tag(name = "Parking API", description = "Operaciones CRUD para parqueaderos")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @Operation(
            summary = "Crear un nuevo parqueadero",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Estructura completa del parqueadero a crear",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ParkingDocument.class),
                            examples = @ExampleObject(value = """
                    {
                      "site": {
                        "name": "Centro Mayor",
                        "rating": "4.4",
                        "covered": true,
                        "location": {
                          "latitude": 4.60971,
                          "longitude": -74.08175
                        },
                        "address": {
                          "street": "Calle 26",
                          "number": 25,
                          "city": "Bogotá",
                          "state": "Cundinamarca",
                          "country": "Colombia"
                        },
                        "floors": [
                          {
                            "number": 1,
                            "slots": [
                              { "id": "1", "status": false, "type": "Carro" }
                            ]
                          }
                        ]
                      }
                    }
                    """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Parqueadero creado exitosamente")
            }
    )
    @PostMapping
    public ParkingDocument createParking(@RequestBody ParkingDocument parking) {
        return parkingService.createParking(parking);
    }

    @Operation(summary = "Obtener todos los parqueaderos", responses = {
            @ApiResponse(responseCode = "200", description = "Listado de parqueaderos")
    })
    @GetMapping
    public List<ParkingDocument> getAllParkings() {
        return parkingService.getAllParkings();
    }

    @Operation(summary = "Obtener parqueadero por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Parqueadero encontrado"),
            @ApiResponse(responseCode = "404", description = "Parqueadero no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ParkingDocument> getParkingById(@PathVariable String id) {
        return parkingService.getParkingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar parqueadero por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Parqueadero actualizado"),
            @ApiResponse(responseCode = "404", description = "Parqueadero no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ParkingDocument> updateParking(@PathVariable String id, @RequestBody ParkingDocument updatedParking) {
        return parkingService.updateParking(id, updatedParking)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar parqueadero por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Parqueadero eliminado"),
            @ApiResponse(responseCode = "404", description = "Parqueadero no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParking(@PathVariable String id) {
        boolean deleted = parkingService.deleteParking(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

