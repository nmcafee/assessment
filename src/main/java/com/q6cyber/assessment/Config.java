package com.q6cyber.assessment;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;


public abstract class Config {
    @Getter
    private static final String locationDescription = "Cumming, GA, United States";
    @Getter
    private static final String geocodeUrl = "https://geocode.xyz";
    @Getter
    private static final String forecastGridUrl = "https://api.weather.gov/points/";

    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
