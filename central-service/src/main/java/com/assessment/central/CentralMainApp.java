package com.assessment.central;

import com.assessment.central.handler.EventConsumer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class CentralMainApp implements CommandLineRunner {

    private final EventConsumer eventConsumer;

    public CentralMainApp(EventConsumer eventConsumer){
        this.eventConsumer = eventConsumer;
    }

    public static void main(String[] args) {
        SpringApplication.run(CentralMainApp.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        eventConsumer.consume();
    }
}