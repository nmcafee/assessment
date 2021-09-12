package com.q6cyber.assessment;

import com.q6cyber.assessment.api.weather.ForecastResponse;
import com.q6cyber.assessment.model.WeatherSummary;
import com.q6cyber.assessment.service.ForecastChecker;

import java.io.IOException;


public class WhatIsTheWeatherApp {

  /*
    This code seeks to create a weather summary for a particular location. (Cumming, GA in this case).
    The weather api requires that we have the latitude and longitude for the location, so we must look
    it up using the geocode api. The code then calls a weather api with the latitude and longitude to
    get a url to the hourly forecast. Finally, another call to the weather api is made using the
    forecast url returned from the previous call to get hourly forecast information.

    ****** PLEASE FOLLOW THE DIRECTIONS BELOW ******

    It is probably not good that all of this logic is in the main method. Clean this code up to make
    it more clean, reusable, and testable as you see fit. Make any changes and create any new classes
    that you like. Also, please follow the directions at the bottom of this file to properly create
    WeatherSummary and System.out.println(..) it.

    ****** OPTIONAL ******

    Add unit tests
   */

    public static void main(String[] args) throws IOException {
        ForecastResponse forecastResponse = new ForecastChecker().checkForecast();
        WeatherSummary weatherSummary = new WeatherSummary(forecastResponse);

        System.out.printf("Time of creation: %s%n", weatherSummary.getTime());
        System.out.printf("Current forecast: %s%n", weatherSummary.getCurrentTemp());
        System.out.printf("Current forecast: %s%n", weatherSummary.getCurrentForecast());
        System.out.printf("Max temperature: %s%n", weatherSummary.getMaxTemp());
        System.out.printf("Average temperature: %s%n", weatherSummary.getAverageTemp());

        System.out.print("Upcoming forecasts: \n\t");
        weatherSummary.getForecasts().forEach(forecast -> System.out.printf("%s, ", forecast));

        System.out.println("\n" + weatherSummary);

    /*
      Create a new class called WeatherSummary from forecastResponsePayload and call
      System.out.println(...) on it with the following requirements:

      - getTime() : the time the WeatherSummary was created
      - getCurrentTemp() : the temperature of the very first period in the payload
      - getCurrentForecast() : the shortForecast of the very first period in the payload
      - getMaxTemp() : the highest temp across all periods in the payload
      - getAverageTemp() : the average temp across all periods in the payload
      - getForecasts() : the unique shortForecast values across all periods

      - should be immutable
      - implement so that Collections.sort on a list of WeatherSummary objects would sort by getTime()
          with the most recent coming first
      - implement so that if multiple WeatherSummary objects are put into a HashSet with the same time,
          only 1 would be retained regardless of the other field values.
      - if System.out.println(weatherSummary) is called, all field values and the class name should
          be printed to the console.

      If it helps, example payloads for each of the api calls can be found under the base of the project
      - geocodeResponseExample.json
      - forecastGridResponseExample.json
      - forecastResponseExample.json

      WeatherSummary class stub:

      package com.q6cyber.assessment;

      import java.util.Date;

      public class WeatherSummary {

        public Date getTime() {
          return null;
        }

        public int getCurrentTemp() {
          return -1;
        }

        public String getCurrentForecast() {
          return null;
        }

        public int getMaxTemp() {
          return -1;
        }

        public int getAverageTemp() {
          return -1;
        }

        public Iterable<String> getForecasts() {
          return null;
        }
      }
     */
    }
}
