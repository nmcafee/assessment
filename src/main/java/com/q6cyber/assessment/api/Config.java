package com.q6cyber.assessment.api;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

public abstract class Config {
    @Getter
    private static final String geocodeUrl = "https://geocode.xyz";

    //@todo decide whether to make urls config variables or leave them alone

    public static ObjectMapper getObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
