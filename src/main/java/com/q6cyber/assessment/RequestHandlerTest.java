package com.q6cyber.assessment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.q6cyber.assessment.api.geocode.GeocodeResponse;
import com.q6cyber.assessment.api.weather.ForecastGridResponse;
import com.q6cyber.assessment.api.weather.ForecastResponse;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestHandlerTest implements RequestHandler {

    private CloseableHttpClient closeableHttpClient;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        CloseableHttpClient closableHttpClient = HttpClients.createDefault();
        ObjectMapper objectMapper = Config.getObjectMapper();
        this.closeableHttpClient = closableHttpClient;
        this.objectMapper = objectMapper;
    }

    @Test
    void geocodePostRequestTest() throws IOException {
        List<NameValuePair> geocodeRequestParameters =
                List.of(
                        new BasicNameValuePair("locate", "Cumming, GA, United States"),
                        new BasicNameValuePair("geoit", "JSON")
                );
        GeocodeResponse geocodeResponse = (GeocodeResponse) RequestHandler.makePostRequest(
                closeableHttpClient,
                objectMapper,
                Config.getGeocodeUrl(),
                GeocodeResponse.class,
                geocodeRequestParameters
        );
        assertNotNull(geocodeResponse);
    }

    @Test
    void forecastGridGetRequestTest() throws IOException {
        ForecastGridResponse forecastGridResponse = (ForecastGridResponse) RequestHandler.makeGetRequest(
                closeableHttpClient,
                objectMapper,
                Config.getForecastGridUrl() + "34.20987,-84.15306",
                ForecastGridResponse.class
        );
        assertNotNull(forecastGridResponse);
    }

    @Test
    void forecastGetRequestTest() throws IOException {
        ForecastResponse forecastResponse = (ForecastResponse) RequestHandler.makeGetRequest(
                closeableHttpClient,
                objectMapper,
                "https://api.weather.gov/gridpoints/FFC/57,107/forecast/hourly",
                ForecastResponse.class
        );
        assertNotNull(forecastResponse);
    }

}