package com.assessment.central.handler;

import com.assessment.central.dto.Event;
import com.assessment.central.dto.MeasurementEvent;
import com.assessment.central.dto.SensorType;
import com.assessment.central.service.AlertService;
import com.assessment.central.service.AlertServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TemperatureEventHandler implements EventHandler {

    private final AlertServiceFactory alertServiceFactory;

    @Value("${temperature.threshold}")
    private Float temperatureThreshold;

    @Override
    public SensorType getSensorType() {
        return SensorType.Temperature;
    }

    public TemperatureEventHandler(AlertServiceFactory alertServiceFactory) {
        this.alertServiceFactory = alertServiceFactory;
    }

    @Override
    public void handleEvent(Event event) {

        if (event instanceof MeasurementEvent measurementEvent) {
            if (measurementEvent.getValue() > temperatureThreshold) {
                alertServiceFactory.getAlertService().raiseAlert("Temperature exceeded threshold: Sensor = " + measurementEvent.getSensorId() + " Value = " + measurementEvent.getValue());
            }
        }
    }
}
