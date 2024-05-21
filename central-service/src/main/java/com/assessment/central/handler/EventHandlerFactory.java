package com.assessment.central.handler;

import com.assessment.central.dto.Event;
import com.assessment.central.dto.MeasurementEvent;
import com.assessment.central.dto.SensorType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EventHandlerFactory {

    private final Map<SensorType, EventHandler> eventHandlerMap;

    private EventHandlerFactory(List<EventHandler> eventHandlers) {
        eventHandlerMap = eventHandlers.stream().collect(Collectors.toUnmodifiableMap(EventHandler::getSensorType, Function.identity()));
    }

    public EventHandler getEventHandler(SensorType sensorType) {
        return eventHandlerMap.get(sensorType);
    }
}
