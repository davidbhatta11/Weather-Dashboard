package com.davidbhatta.weather_dashboard.Service;

import com.davidbhatta.weather_dashboard.model.WeatherData;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Map;

@Service
public class WeatherService {

    // ✅ These belong HERE — outside the method, inside the class
    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    // ✅ Only ONE getWeather method
    public WeatherData getWeather(String city) {
        RestTemplate restTemplate = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("q", city)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null) return null;

        WeatherData weatherData = new WeatherData();

        weatherData.setCity((String) response.get("name"));
        Map<String, Object> sys = (Map<String, Object>) response.get("sys");
        weatherData.setCountry((String) sys.get("country"));

        Map<String, Object> main = (Map<String, Object>) response.get("main");
        weatherData.setTemperature(((Number) main.get("temp")).doubleValue());
        weatherData.setFeelsLike(((Number) main.get("feels_like")).doubleValue());
        weatherData.setHumidity(((Number) main.get("humidity")).intValue());

        Map<String, Object> weather = (Map<String, Object>)
                ((java.util.List<?>) response.get("weather")).get(0);
        String desc = (String) weather.get("description");
        weatherData.setDescription(desc.substring(0, 1).toUpperCase() + desc.substring(1));
        weatherData.setIcon((String) weather.get("icon"));

        return weatherData;
    }
}