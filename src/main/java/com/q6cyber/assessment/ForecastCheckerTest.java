package com.q6cyber.assessment;

import com.q6cyber.assessment.api.weather.ForecastResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class ForecastCheckerTest {
    private ForecastResponse forecastResponse;

    @BeforeEach
    void startup() throws IOException {
        ForecastChecker forecastChecker = new ForecastChecker();
        this.forecastResponse = forecastChecker.checkForecast();
    }

    @Test
    void weatherForecastTest() {
        assertNotNull(forecastResponse);
    }

    @Test
    void weatherSummaryTest() {
        WeatherSummary weatherSummary = new WeatherSummary(forecastResponse);
        assertNotNull(weatherSummary);
    }
}