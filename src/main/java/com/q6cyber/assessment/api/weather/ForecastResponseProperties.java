package com.q6cyber.assessment.api.weather;

import java.util.Arrays;

public class ForecastResponseProperties {

  private ForecastResponsePeriod[] periods;

  public ForecastResponsePeriod[] getPeriods() {
    return periods;
  }

  @Override
  public String toString() {
    return "ForecastResponseProperties{" +
        "periods=" + Arrays.toString(periods) +
        '}';
  }
}
