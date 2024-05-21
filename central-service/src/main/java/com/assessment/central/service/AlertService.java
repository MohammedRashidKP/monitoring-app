package com.assessment.central.service;

public interface AlertService {

    String getType();

    void raiseAlert(String alertMessage);
}
