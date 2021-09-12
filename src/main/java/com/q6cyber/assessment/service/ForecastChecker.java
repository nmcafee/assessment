package com.q6cyber.assessment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.q6cyber.assessment.Config;
import com.q6cyber.assessment.api.geocode.GeocodeResponse;
import com.q6cyber.assessment.api.weather.ForecastGridResponse;
import com.q6cyber.assessment.api.weather.ForecastResponse;
import lombok.extern.java.Log;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.List;


@Log
public class ForecastChecker implements RequestHandler {
    private final String locationDescription;

    public ForecastChecker () {
        this.locationDescription = Config.getLocationDescription();
    }

    // Optionally pass in a desired location
    public ForecastChecker (String locationDescription) {
        this.locationDescription = locationDescription;
    }

    private GeocodeResponse getGoecodeResponse(
            CloseableHttpClient httpClient,
            ObjectMapper objectMapper) throws IOException {
        List<NameValuePair> geocodeRequestParameters =
                List.of(
                        new BasicNameValuePair("locate", locationDescription),
                        new BasicNameValuePair("geoit", "JSON")
                );
        log.info("Waiting for geocode response");
        return (GeocodeResponse) RequestHandler.makePostRequest(
                httpClient,
                objectMapper,
                Config.getGeocodeUrl(),
                GeocodeResponse.class,
                geocodeRequestParameters
        );
    }

    // Get the url to the weather forecast for Cumming, GA based on the latitude and longitude from the weather api
    private ForecastGridResponse getForecastGridResponse(
            CloseableHttpClient httpClient,
            ObjectMapper objectMapper,
            GeocodeResponse geocodeResponse) throws IOException {
        log.info("Waiting for forecast grid response");
        return (ForecastGridResponse) RequestHandler.makeGetRequest(
                httpClient,
                objectMapper,
                Config.getForecastGridUrl() + geocodeResponse.getLatt() + "," + geocodeResponse.getLongt(),
                ForecastGridResponse.class
        );
    }

    // Get the hourly forecast for Cumming, GA using the hourly forecast url also calling the weather api
    private ForecastResponse getForecastResponse(
            CloseableHttpClient httpClient,
            ObjectMapper objectMapper,
            ForecastGridResponse forecastGridResponse) throws IOException {
        log.info("Waiting for forecast response");
        return (ForecastResponse) RequestHandler.makeGetRequest(
                httpClient,
                objectMapper,
                forecastGridResponse.getProperties().getForecastHourly(),
                ForecastResponse.class
        );
    }

    public ForecastResponse checkForecast() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ObjectMapper objectMapper = Config.getObjectMapper();

        GeocodeResponse geocodeResponse = getGoecodeResponse(httpClient, objectMapper);
        ForecastGridResponse forecastGridResponse = getForecastGridResponse(httpClient, objectMapper, geocodeResponse);
        ForecastResponse forecastResponse = getForecastResponse(httpClient, objectMapper, forecastGridResponse);

        httpClient.close();
        return forecastResponse;
    }
}
