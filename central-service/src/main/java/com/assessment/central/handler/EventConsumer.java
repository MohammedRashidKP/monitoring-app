package com.assessment.central.handler;

import com.assessment.central.dto.Event;
import com.assessment.central.dto.MeasurementEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Delivery;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.Receiver;

import java.io.IOException;

@Component
@Slf4j
public class EventConsumer {

    @Value("${queue.name}")
    private String queueName;

    private final Receiver receiver;

    private final Mono<Connection> connectionMono;

    private final EventHandlerFactory eventHandlerFactory;

    public EventConsumer(Mono<Connection> connectionMono,
                         Receiver receiver,
                         EventHandlerFactory eventHandlerFactory){
        this.connectionMono = connectionMono;
        this.receiver = receiver;
        this.eventHandlerFactory = eventHandlerFactory;
    }

    public void consume() {

        receiver.consumeNoAck(queueName)
                .map(this::assembleEvent)
                .subscribe(event -> eventHandlerFactory.getEventHandler(event.getSensorType()).handleEvent(event));
    }

    private Event assembleEvent(Delivery message) {
        MeasurementEvent measurementEvent;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            measurementEvent = objectMapper.readValue(message.getBody(), MeasurementEvent.class);
            log.info(String.valueOf(measurementEvent));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return measurementEvent;
    }

    @PreDestroy
    public void close() throws Exception {
        connectionMono.block().close();
    }
}
