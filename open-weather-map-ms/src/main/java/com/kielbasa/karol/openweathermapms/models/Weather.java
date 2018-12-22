package com.kielbasa.karol.openweathermapms.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Weather {
    private List<CityWeather> cityWeatherList = new LinkedList<>();
    private LocalDateTime lastUpdate;
}
