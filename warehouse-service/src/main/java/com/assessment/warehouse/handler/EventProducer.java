package com.assessment.warehouse.handler;

import com.assessment.warehouse.dto.MeasurementEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Connection;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.Sender;

import java.util.Objects;

@Component
@Slf4j
public class EventProducer {


    private final Sender sender;

    @Value("${queue.name}")
    private String queueName;

    private final Mono<Connection> connectionMono;

    public EventProducer(Mono<Connection> connectionMono,
                         Sender sender) {
        this.connectionMono = connectionMono;
        this.sender = sender;
    }

    public void handle(MeasurementEvent message) throws JsonProcessingException {

        Mono<OutboundMessage> outboundMessageMono =
                Mono.just(new OutboundMessage("",
                        queueName, Objects.requireNonNull(new ObjectMapper().writeValueAsBytes(message))));
        sender.declareQueue(QueueSpecification.queue(queueName))
                .thenMany(sender.sendWithPublishConfirms(outboundMessageMono))
                .doOnError(e -> log.error("Send failed", e))
                .subscribe(m -> {
                    if (m.isAck()) {
                        log.info("Message [" + message + "] sent");
                    }
                });
    }

    @PreDestroy
    public void close() throws Exception {
        Objects.requireNonNull(connectionMono.block()).close();
    }
}
