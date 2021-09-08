package com.q6cyber.assessment.api.geocode;

public class GeocodeResponse {

  private String longt;
  private String latt;

  public String getLongt() {
    return longt;
  }

  public String getLatt() {
    return latt;
  }

  @Override
  public String toString() {
    return "GeocodeResponse{" +
        "longt='" + longt + '\'' +
        ", latt='" + latt + '\'' +
        '}';
  }
}
