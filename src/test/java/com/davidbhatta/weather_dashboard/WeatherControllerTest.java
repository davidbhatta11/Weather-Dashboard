package com.davidbhatta.weather_dashboard;

import com.davidbhatta.weather_dashboard.Service.WeatherService;
import com.davidbhatta.weather_dashboard.model.WeatherData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    void testIndexPage_ReturnsOk() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void testSearchCity_ReturnsWeatherData() throws Exception {
        WeatherData mockData = new WeatherData();
        mockData.setCity("Winnipeg");
        mockData.setCountry("CA");
        mockData.setTemperature(5.0);
        mockData.setHumidity(80);
        mockData.setDescription("Cloudy");

        when(weatherService.getWeather("Winnipeg")).thenReturn(mockData);

        mockMvc.perform(post("/weather")
                        .param("city", "Winnipeg"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("weather"));
    }

    @Test
    void testSearchCity_InvalidCity_ReturnsError() throws Exception {
        when(weatherService.getWeather("InvalidCity123"))
                .thenThrow(new RuntimeException("City not found"));

        mockMvc.perform(post("/weather")
                        .param("city", "InvalidCity123"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("error"));
    }
}