package com.assessment.central.handler;

import com.assessment.central.dto.Event;
import com.assessment.central.dto.MeasurementEvent;
import com.assessment.central.dto.SensorType;
import com.assessment.central.service.AlertServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HumidityEventHandler implements EventHandler {

    private final AlertServiceFactory alertServiceFactory;

    @Value("${humidity.threshold}")
    private Float humidityThreshold;

    @Override
    public SensorType getSensorType() {
        return SensorType.Humidity;
    }

    public HumidityEventHandler(AlertServiceFactory alertServiceFactory) {
        this.alertServiceFactory = alertServiceFactory;
    }

    @Override
    public void handleEvent(Event event) {

        if (event instanceof MeasurementEvent measurementEvent) {
            if (measurementEvent.getValue() > humidityThreshold) {
                alertServiceFactory.getAlertService().raiseAlert("Humidity exceeded threshold: Sensor = " + measurementEvent.getSensorId() + " Value = " + measurementEvent.getValue() + "%");
            }
        }
    }
}
