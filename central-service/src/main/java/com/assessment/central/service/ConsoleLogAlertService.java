package com.assessment.central.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsoleLogAlertService implements AlertService{
    @Override
    public String getType() {
        return "console";
    }

    @Override
    public void raiseAlert(String alertMessage) {
        log.info(alertMessage);
    }
}
