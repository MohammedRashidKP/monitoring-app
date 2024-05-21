package com.assessment.central.service;

import com.assessment.central.dto.SensorType;
import com.assessment.central.handler.EventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AlertServiceFactory {
    @Value("${alert.type}")
    private String alertType;
    private final Map<String, AlertService> alertServiceMap;

    private AlertServiceFactory(List<AlertService> alertServices) {
        alertServiceMap = alertServices.stream().collect(Collectors.toUnmodifiableMap(AlertService::getType, Function.identity()));
    }

    public AlertService getAlertService() {
        return alertServiceMap.get(alertType);
    }
}
