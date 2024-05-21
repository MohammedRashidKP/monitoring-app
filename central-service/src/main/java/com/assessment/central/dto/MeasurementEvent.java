package com.assessment.central.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString
public class MeasurementEvent extends Event implements Serializable {

    public MeasurementEvent(SensorType sensorType, String sensorId, Float value){
        super.setSensorType(sensorType);
        this.sensorId = sensorId;
        this.value = value;
    }
    private String sensorId;

    private Float value;
}
