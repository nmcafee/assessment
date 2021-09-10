package com.q6cyber.assessment;


import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.q6cyber.assessment.api.Config;
import com.q6cyber.assessment.api.geocode.GeocodeResponse;
import com.q6cyber.assessment.api.weather.ForecastGridResponse;
import com.q6cyber.assessment.api.weather.ForecastResponse;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;


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

    private static Object makeGetRequest(
            CloseableHttpClient httpClient,
            ObjectMapper objectMapper,
            String url,
            Class<?> classToDeserialize
    ) throws IOException {
        HttpGet request = new HttpGet(url);
        return sendRequest(httpClient, objectMapper, request, classToDeserialize);
    }

    private static Object makePostRequest(
            CloseableHttpClient httpClient,
            ObjectMapper objectMapper,
            String url,
            Class<?> classToDeserialize,
            List<NameValuePair> params
    ) throws IOException {
        HttpPost request = new HttpPost(url);
        request.setEntity(new UrlEncodedFormEntity(params, UTF_8));
        return sendRequest(httpClient, objectMapper, request, classToDeserialize);
    }

    private static Object sendRequest(
            CloseableHttpClient httpClient,
            ObjectMapper objectMapper,
            HttpUriRequest request,
            Class<?> classToDeserialize
    ) throws IOException {
        CloseableHttpResponse httpResponse = httpClient.execute(request);
        Object payload = objectMapper.readValue(httpResponse.getEntity().getContent(), classToDeserialize);
        httpResponse.close();
        return payload;
    }

    // Find the latitude and longitude of Cumming, GA from the geocode api
    private static GeocodeResponse getGoecodeResponse(CloseableHttpClient httpClient, ObjectMapper objectMapper) throws IOException {

        List<NameValuePair> geocodeRequestParameters =
                List.of(
                        new BasicNameValuePair("locate", "Cumming, GA, United States"),
                        new BasicNameValuePair("geoit", "JSON")
                );

        return (GeocodeResponse) makePostRequest(
                httpClient,
                objectMapper,
                Config.getGeocodeUrl(),
                GeocodeResponse.class,
                geocodeRequestParameters
        );
    }

    // Get the url to the weather forecast for Cumming, GA based on the latitude and longitude from the weather api
    private static ForecastGridResponse getForecastGridResponse(CloseableHttpClient httpClient, ObjectMapper objectMapper, GeocodeResponse geocodeResponse) throws IOException {
        return (ForecastGridResponse) makeGetRequest(
                httpClient,
                objectMapper,
                String.format("https://api.weather.gov/points/%s,%s", geocodeResponse.getLatt(), geocodeResponse.getLongt()),
                ForecastGridResponse.class
        );
    }

    // Get the hourly forecast for Cumming, GA using the hourly forecast url also calling the weather api
    private static ForecastResponse getForecastResponse(CloseableHttpClient httpClient, ObjectMapper objectMapper, ForecastGridResponse forecastGridResponse) throws IOException {
        return (ForecastResponse) makeGetRequest(
                httpClient,
                objectMapper,
                forecastGridResponse.getProperties().getForecastHourly(),
                ForecastResponse.class
        );
    }

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ObjectMapper objectMapper = Config.getObjectMapper();

        GeocodeResponse geocodeResponse = getGoecodeResponse(httpClient, objectMapper);
        ForecastGridResponse forecastGridResponse = getForecastGridResponse(httpClient, objectMapper, geocodeResponse);
        ForecastResponse forecastResponse = getForecastResponse(httpClient, objectMapper, forecastGridResponse);

        System.out.println(forecastResponse);

        httpClient.close();
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
