package com.assessment.warehouse.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class MeasurementEvent extends Event implements Serializable {

    public MeasurementEvent(SensorType sensorType, String sensorId, Float value){
        super.setSensorType(sensorType);
        this.sensorId = sensorId;
        this.value = value;
    }
    private String sensorId;

    private Float value;
}
