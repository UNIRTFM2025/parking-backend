package com.parking.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.dto.SlotIoTDTO;
import com.parking.entities.Slot;
import com.parking.services.SpaceService;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingSensorClient {

    @Autowired
    private SpaceService spaceService;

    private static final String BROKER = "tcp://broker.hivemq.com:1883";
    private static final String CLIENT_ID = "spring-parking-subscriber";
    private static final String TOPIC = "iot/parking/slot";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void start() {
        try {
            MqttClient client = new MqttClient(BROKER, CLIENT_ID);
            client.connect();

            client.subscribe(TOPIC, (topic, msg) -> {
                String json = new String(msg.getPayload());
                try {
                    SlotIoTDTO status = objectMapper.readValue(json, SlotIoTDTO.class);
                    System.out.println("Mensaje recibido: " + status);
                    // Aquí puedes guardar en BD o enviar a otro microservicio
                    Optional<Slot> update = spaceService.updateSlot(status.getId(), status.getParkingId(), status.getFloorsId(), status.getSlotId(), status.isStatus());
                    if (update.isPresent()) {
                        System.out.println("Mensaje procesado: " + status);
                    }
                } catch (Exception e) {
                    System.err.println("Error al parsear el JSON: " + e.getMessage());
                }
            });

            System.out.println("Suscrito al tópico MQTT: " + TOPIC);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
