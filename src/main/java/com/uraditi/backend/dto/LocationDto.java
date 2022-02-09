package com.uraditi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDto {
    //x - latitude
    double x;
    //y - longitude
    double y;
    String name;
}
