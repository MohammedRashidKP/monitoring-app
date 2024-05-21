package com.assessment.warehouse.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class Event implements Serializable {
    protected SensorType sensorType;

}
