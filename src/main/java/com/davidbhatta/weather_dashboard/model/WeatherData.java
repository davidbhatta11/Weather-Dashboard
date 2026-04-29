package com.davidbhatta.weather_dashboard.model;

import lombok.Data;

@Data
public class WeatherData {
    private String city;
    private String country;
    private double temperature;
    private double feelsLike;
    private int humidity;
    private String description;
    private String icon;
}