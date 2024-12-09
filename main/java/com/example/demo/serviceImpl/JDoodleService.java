package com.example.demo.serviceImpl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class JDoodleService {

    private static final String clientId = "ded54f885f41b84f18425c55d613db1";  // Replace with your client ID
    private static final String clientSecret = "f6caf1037f3bc1eb4cdeb767e180aa563224e5e73b813be0d86c525eca3f11e8"; // Replace with your client secret

    private static final String JDoodle_API_URL = "https://api.jdoodle.com/v1/execute";

    public String executeCode(String script, String language, String versionIndex, String input) {
        RestTemplate restTemplate = new RestTemplate();

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request body
        Map<String, String> body = new HashMap<>();
        body.put("clientId", clientId);
        body.put("clientSecret", clientSecret);
        body.put("script", script);
        body.put("language", language);
        body.put("versionIndex", versionIndex);
        body.put("stdin", input);


        // Wrap body in HttpEntity
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        // Send request to JDoodle API
        ResponseEntity<String> response = restTemplate.exchange(JDoodle_API_URL, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }
}
