package com.assessment.warehouse.handler;

import com.assessment.warehouse.dto.MeasurementEvent;
import com.assessment.warehouse.dto.SensorType;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;

import java.util.Map;

@MessageEndpoint
@Slf4j
public class UdpInboundMessageHandler {

    private final EventProducer eventProducer;

    public UdpInboundMessageHandler(EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

//    @ServiceActivator(inputChannel = "inboundChannel")
    public void handeMessage(Message message, @Headers Map<String, Object> headerMap) throws JsonProcessingException {

        log.info("Received UDP message: {}", new String((byte[]) message.getPayload()));
        handleMessage(new String((byte[]) message.getPayload()));
    }

    public void handleMessage(String message) throws JsonProcessingException {

        String[] measurement = message.split(";");

        eventProducer.handle(
                new MeasurementEvent(measurement[0].substring(10, 12).startsWith("t") ? SensorType.Temperature : SensorType.Humidity,
                        measurement[0].substring(10, 12),
                        Float.valueOf(measurement[1].substring(6)))
        );
    }
}
