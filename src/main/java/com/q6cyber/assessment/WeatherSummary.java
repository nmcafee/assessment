package com.q6cyber.assessment;

import com.q6cyber.assessment.api.weather.ForecastResponse;
import com.q6cyber.assessment.api.weather.ForecastResponsePeriod;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Date;

@ToString
final class WeatherSummary {
    @Getter
    private final Date time;
    private final ForecastResponse forecastResponse;

    public WeatherSummary (ForecastResponse forecastResponse) {
        this.forecastResponse = forecastResponse;
        this.time = new Date();
    }

    public int getCurrentTemp() {
        return forecastResponse.getProperties().getPeriods()[0].getTemperature();
    }

    public String getCurrentForecast() {
        return forecastResponse.getProperties().getPeriods()[0].getShortForecast();
    }

    public int getMaxTemp() {
        return Arrays.stream(forecastResponse.getProperties().getPeriods()).parallel()
                .mapToInt(ForecastResponsePeriod::getTemperature)
                .max().orElseThrow();
    }

    public int getAverageTemp() {
        return Arrays.stream(forecastResponse.getProperties().getPeriods()).parallel()
                .mapToInt(ForecastResponsePeriod::getTemperature)
                .sum()
                / forecastResponse.getProperties().getPeriods().length;
    }

    public Iterable<String> getForecasts() {
        return Arrays.stream(forecastResponse.getProperties().getPeriods())
                .map(ForecastResponsePeriod::getShortForecast).distinct()::iterator;
    }
}
