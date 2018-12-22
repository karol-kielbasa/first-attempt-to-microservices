package com.kielbasa.karol.openweathermapms.models;

import lombok.Builder;

@Builder
public class CityWeather {
    private String city;
    private Double temperature;
    private String description;
}
