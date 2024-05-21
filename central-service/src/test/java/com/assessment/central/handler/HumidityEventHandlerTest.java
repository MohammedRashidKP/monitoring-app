package com.assessment.central.handler;

import com.assessment.central.dto.Event;
import com.assessment.central.dto.MeasurementEvent;
import com.assessment.central.dto.SensorType;
import com.assessment.central.service.AlertService;
import com.assessment.central.service.AlertServiceFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HumidityEventHandlerTest {

    @Mock
    private AlertServiceFactory alertServiceFactory;

    @Mock
    private AlertService alertService;

    @Test
    void testThresholdExceededAndAlertRaised() {

        Event event = new MeasurementEvent(SensorType.Humidity, "h1", 55F);
        HumidityEventHandler humidityEventHandler = new HumidityEventHandler(alertServiceFactory);
        ReflectionTestUtils.setField(humidityEventHandler, "humidityThreshold", 50F);
        when(alertServiceFactory.getAlertService()).thenReturn(alertService);
        humidityEventHandler.handleEvent(event);
        verify(alertServiceFactory, times(1)).getAlertService();
    }

    @Test
    void testThresholdNotExceededAndAlertNotRaised() {

        Event event = new MeasurementEvent(SensorType.Humidity, "h1", 30F);
        HumidityEventHandler humidityEventHandler = new HumidityEventHandler(alertServiceFactory);
        ReflectionTestUtils.setField(humidityEventHandler, "humidityThreshold", 50F);
        humidityEventHandler.handleEvent(event);
        verify(alertServiceFactory, times(0)).getAlertService();
    }

}