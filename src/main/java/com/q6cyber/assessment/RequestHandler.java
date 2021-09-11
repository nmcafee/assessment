package com.q6cyber.assessment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;


public interface RequestHandler {
    static Object makeGetRequest(
            CloseableHttpClient httpClient,
            ObjectMapper objectMapper,
            String url,
            Class<?> classToDeserialize
    ) throws IOException {
        HttpGet request = new HttpGet(url);
        return sendRequest(httpClient, objectMapper, request, classToDeserialize);
    }

    static Object makePostRequest(
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
}
