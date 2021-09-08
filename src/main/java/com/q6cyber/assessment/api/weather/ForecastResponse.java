package com.q6cyber.assessment.api.weather;

public class ForecastResponse {

  private ForecastResponseProperties properties;

  public ForecastResponseProperties getProperties() {
    return properties;
  }

  @Override
  public String toString() {
    return "ForecastResponse{" +
        "properties=" + properties +
        '}';
  }
}
