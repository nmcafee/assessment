package com.q6cyber.assessment.test;

import com.q6cyber.assessment.service.ForecastChecker;
import com.q6cyber.assessment.api.weather.ForecastResponse;
import com.q6cyber.assessment.model.WeatherSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class WhatIsTheWeatherAppTest {
    // implement so that Collections.sort on a list of WeatherSummary objects would sort by getTime()
    //with the most recent coming first
    @Test
    void comparatorTest() throws IOException, InterruptedException {
        ForecastChecker forecastChecker = new ForecastChecker();
        ForecastResponse forecastResponse = forecastChecker.checkForecast();
        List<WeatherSummary> weatherList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            WeatherSummary tempWeatherSummary = new WeatherSummary(forecastResponse);
            Thread.sleep(1000);
            weatherList.add(tempWeatherSummary);
        }

        Collections.sort(weatherList);

        for (int i = 0; i < weatherList.size() - 1; i++) {
            WeatherSummary currentSummary = weatherList.get(i);
            WeatherSummary nextSummary = weatherList.get(i + 1);

            assertTrue(nextSummary.getTime().before(currentSummary.getTime()));
        }
    }

    // implement so that if multiple WeatherSummary objects are put into a HashSet with the same time,
    //only 1 would be retained regardless of the other field values.
    @Test
    void hashsetDateDistinctionTest() throws IOException {
        HashSet<WeatherSummary> hashSet = new HashSet<>();
        ForecastChecker forecastChecker = new ForecastChecker();
        ForecastResponse forecastResponse = forecastChecker.checkForecast();
        WeatherSummary weatherSummaryTemp = new WeatherSummary(forecastResponse);
        for (int i = 0; i < 5; i++) {
            hashSet.add(weatherSummaryTemp);
        }
        assertEquals(1, hashSet.size());
    }
}