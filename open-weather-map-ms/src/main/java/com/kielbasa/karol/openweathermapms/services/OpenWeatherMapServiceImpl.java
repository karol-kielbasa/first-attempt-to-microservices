package com.kielbasa.karol.openweathermapms.services;

import com.kielbasa.karol.openweathermapms.models.CityWeather;
import com.kielbasa.karol.openweathermapms.models.Weather;
import com.kielbasa.karol.openweathermapms.repositories.OpenWeatherMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenWeatherMapServiceImpl implements OpenWeatherMapService {

    private OpenWeatherMapRepository openWeatherMapRepository;

    @Value("${open.weather.map.key}")
    private String key;

    @Value("${open.weather.map.url}")
    private String url;

    @Value("${open.weather.map.default.cities}")
    private List<String> cities;

    @Autowired
    public OpenWeatherMapServiceImpl(OpenWeatherMapRepository openWeatherMapRepository) {
        this.openWeatherMapRepository = openWeatherMapRepository;
    }

    // This method invokes every 15 min
    @Scheduled(fixedRate = 900000)
    public void getWeatherFromAPI() {
        RestTemplate restTemplate = new RestTemplate();
        Weather weather = new Weather();
        for (String city : cities) {
            Map<String, Object> res = restTemplate.getForObject(getURL(city), Map.class, new HashMap<>());
            CityWeather cityWeather = getCityWeatherFromResponse(res, city);
            weather.getCityWeatherList().add(cityWeather);
        }
        weather.setLastUpdate(LocalDateTime.now());
        openWeatherMapRepository.save(weather);
    }

    private CityWeather getCityWeatherFromResponse(Map<String, Object> res, String city) {
        Map<String, Object> main = (Map<String, Object>) res.get("main");
        List<Object> weather = (List<Object>) res.get("weather");
        Map<String, Object> description = (Map<String, Object>) weather.get(0);
        return CityWeather.builder()
                .city(city)
                .temperature((Double) main.get("temp"))
                .description((String) description.get("main"))
                .build();

    }

    @Override
    public void getWeatherFromDB() {

    }

    private String getURL(String city) {
        return url + city + key;
    }
}
