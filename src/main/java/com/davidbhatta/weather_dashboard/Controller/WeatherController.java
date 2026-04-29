package com.davidbhatta.weather_dashboard.Controller;

import com.davidbhatta.weather_dashboard.model.WeatherData;
import com.davidbhatta.weather_dashboard.Service.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // Handles GET request — shows the page
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Handles POST request — when user submits the search form
    @PostMapping("/weather")
    public String getWeather(@RequestParam String city, Model model) {
        try {
            WeatherData weather = weatherService.getWeather(city);
            model.addAttribute("weather", weather);
        } catch (Exception e) {
            model.addAttribute("error", "City not found. Please try again.");
        }
        return "index";
    }

}