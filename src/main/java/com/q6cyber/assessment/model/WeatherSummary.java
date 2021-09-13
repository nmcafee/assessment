package com.q6cyber.assessment.model;

import com.q6cyber.assessment.api.weather.ForecastResponse;
import com.q6cyber.assessment.api.weather.ForecastResponsePeriod;
import lombok.*;

import java.util.Arrays;
import java.util.Date;


@Value
@AllArgsConstructor // prevents an 'all args constructor' bc @value creates one by default
public class WeatherSummary implements Comparable<WeatherSummary> {
    // Sort collections of this object by time, most recent first
    @Override
    public int compareTo(WeatherSummary o) {
        return o.getTime().compareTo(this.getTime());
    }

    Date time;
    int currentTemp;
    String currentForecast;
    int maxTemp;
    int averageTemp;
    Iterable<String> forecasts;

    public WeatherSummary(ForecastResponse forecastResponse) {
        this.time = new Date();
        this.currentTemp = forecastResponse.getProperties().getPeriods()[0].getTemperature();
        this.currentForecast = forecastResponse.getProperties().getPeriods()[0].getShortForecast();
        this.maxTemp = Arrays.stream(forecastResponse.getProperties().getPeriods()).parallel()
                .mapToInt(ForecastResponsePeriod::getTemperature)
                .max().orElseThrow();
        this.averageTemp = Arrays.stream(forecastResponse.getProperties().getPeriods()).parallel()
                .mapToInt(ForecastResponsePeriod::getTemperature)
                .sum()
                / forecastResponse.getProperties().getPeriods().length;
        this.forecasts = Arrays.stream(forecastResponse.getProperties().getPeriods())
                .map(ForecastResponsePeriod::getShortForecast).distinct()::iterator;
    }
}
