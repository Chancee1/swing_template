package utils;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.net.URI;

public class HTTPRequests {
    private HttpClient httpClient;

    public HTTPRequests(){
        httpClient = HttpClient.newHttpClient();
    }

    private HttpResponse<String> sendGetRequest(String URL) {

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(URL)) // Replace with your Spring Boot endpoint URL
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HttpResponse<String> sendPostRequest(String url, String requestBody, String contentType) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", contentType)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
