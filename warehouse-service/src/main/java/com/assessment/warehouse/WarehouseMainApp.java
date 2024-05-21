package com.assessment.warehouse;

import com.assessment.warehouse.handler.UdpInboundMessageHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class WarehouseMainApp implements CommandLineRunner {

    @Autowired
    private UdpInboundMessageHandler udpInboundMessageHandler;

    public static void main(String[] args) {
        SpringApplication.run(WarehouseMainApp.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        List<String> messages = Arrays.asList("sensor_id=t1;value=20",
                "sensor_id=t1;value=90",
                "sensor_id=h1;value=15",
                "sensor_id=t1;value=80");
        messages.forEach(message -> {
            try {
                udpInboundMessageHandler.handleMessage(message);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
//        udpInboundMessageHandler.handleMessage(message);
    }
}