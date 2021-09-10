package com.q6cyber.assessment;

import com.q6cyber.assessment.api.weather.ForecastResponse;
import com.q6cyber.assessment.api.weather.ForecastResponsePeriod;

import java.util.Arrays;
import java.util.Date;

final class WeatherSummary {

    private final ForecastResponse forecastResponse;

    public WeatherSummary (ForecastResponse forecastResponse) {
        this.forecastResponse = forecastResponse;
    }

    public Date getTime() {
        //return forecastResponse.getProperties().getUpdated(); //@todo this to date or new date/ generated at? it says the class name, so probably new Date()
        return new Date();
    }

    public int getCurrentTemp() {
        return forecastResponse.getProperties().getPeriods()[0].getTemperature();
    }

    public String getCurrentForecast() {
        return forecastResponse.getProperties().getPeriods()[0].getShortForecast();
    }
    //@todo consider building a cached int array/collection to prevent redoing same calc
    public int getMaxTemp() {
        return Arrays.stream(forecastResponse.getProperties().getPeriods())
                .mapToInt(ForecastResponsePeriod::getTemperature)
                .max().orElseThrow();
    }

    public int getAverageTemp() {
        return Arrays.stream(forecastResponse.getProperties().getPeriods())
                .mapToInt(ForecastResponsePeriod::getTemperature)
                .sum()
                / forecastResponse.getProperties().getPeriods().length;
    }

    public Iterable<String> getForecasts() { //@todo prompt says " 'unique' shortForecast values". probably means distinct / no replication.
        return Arrays.stream(forecastResponse.getProperties().getPeriods())
                .map(ForecastResponsePeriod::getShortForecast)::iterator;
    }
}
