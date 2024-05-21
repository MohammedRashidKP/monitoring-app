package com.assessment.central.handler;

import com.assessment.central.dto.Event;
import com.assessment.central.dto.SensorType;

public interface EventHandler {
    SensorType getSensorType();

    void handleEvent(Event event);
}
