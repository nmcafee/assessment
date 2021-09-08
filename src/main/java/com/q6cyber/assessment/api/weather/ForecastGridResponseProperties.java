package com.q6cyber.assessment.api.weather;

public class ForecastGridResponseProperties {

  private String forecastHourly;

  public String getForecastHourly() {
    return forecastHourly;
  }

  @Override
  public String toString() {
    return "ForecastGridResponseProperties{" +
        "forecastHourly='" + forecastHourly + '\'' +
        '}';
  }
}
