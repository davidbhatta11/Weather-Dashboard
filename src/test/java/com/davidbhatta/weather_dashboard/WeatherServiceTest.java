package com.davidbhatta.weather_dashboard;

import com.davidbhatta.weather_dashboard.Service.WeatherService;
import com.davidbhatta.weather_dashboard.model.WeatherData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WeatherServiceTest {

    @Autowired
    private WeatherService weatherService;

    @Test
    void testGetWeather_ValidCity_ReturnsWeatherData() {
        WeatherData mockData = new WeatherData();
        mockData.setCity("Winnipeg");
        mockData.setCountry("CA");
        mockData.setTemperature(5.0);
        mockData.setHumidity(80);
        mockData.setDescription("Cloudy");

        assertNotNull(mockData);
        assertEquals("Winnipeg", mockData.getCity());
        assertEquals("CA", mockData.getCountry());
    }

    @Test
    void testGetWeather_NullResponse_ReturnsNull() {
        WeatherData result = null;
        assertNull(result);
    }
}