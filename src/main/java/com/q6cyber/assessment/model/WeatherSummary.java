package com.q6cyber.assessment.model;

import com.q6cyber.assessment.api.weather.ForecastResponse;
import com.q6cyber.assessment.api.weather.ForecastResponsePeriod;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Date;


@ToString
@EqualsAndHashCode
public final class WeatherSummary implements Comparable<WeatherSummary> {
    // Sort collections of this object by time, most recent first
    @Override
    public int compareTo(WeatherSummary o) {
        return o.getTime().compareTo(this.getTime());
    }

    private final ForecastResponse forecastResponse;

    @Getter private final Date time;
    @Getter private final int currentTemp;
    @Getter private final String currentForecast;
    @Getter private final int maxTemp;
    @Getter private final int averageTemp;
    @Getter private final Iterable<String> forecasts;

    public WeatherSummary(ForecastResponse forecastResponse) {
        this.forecastResponse = forecastResponse;
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
