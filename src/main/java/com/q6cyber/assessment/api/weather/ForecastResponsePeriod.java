package com.q6cyber.assessment.api.weather;

import java.util.Date;

public class ForecastResponsePeriod {
  private Date startTime;
  private int temperature;
  private String shortForecast;

  public Date getStartTime() {
    return startTime;
  }

  public int getTemperature() {
    return temperature;
  }

  public String getShortForecast() {
    return shortForecast;
  }

  @Override
  public String toString() {
    return "ForecastResponsePeriod{" +
        "startTime=" + startTime +
        ", temperature=" + temperature +
        ", shortForecast='" + shortForecast + '\'' +
        '}';
  }
}
