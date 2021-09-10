package com.q6cyber.assessment.api.weather;

import lombok.Data;
import lombok.Getter;

import java.util.Arrays;

//@todo maybe just need setter annot.
//@todo delete updated if we don't use it, undo lombok
@Data
public class ForecastResponseProperties {

  private String updated;

  private ForecastResponsePeriod[] periods;

//  public ForecastResponsePeriod[] getPeriods() {
//    return periods;
//  }

  @Override
  public String toString() {
    return "ForecastResponseProperties{" +
        "periods=" + Arrays.toString(periods) +
        '}';
  }
}
