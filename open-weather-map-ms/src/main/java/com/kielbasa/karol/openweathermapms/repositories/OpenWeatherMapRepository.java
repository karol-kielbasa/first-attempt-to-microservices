package com.kielbasa.karol.openweathermapms.repositories;

import com.kielbasa.karol.openweathermapms.models.Weather;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OpenWeatherMapRepository extends MongoRepository<Weather, String> {
}
