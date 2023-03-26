package com.example.warehouse.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DistributionCentreService {
    private final RestTemplate restTemplate;
    @Value("${distribution.centre.api.url}")
    private String distributionCentreApiUrl;

    public DistributionCentreService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<DistributionCentre> getDistributionCentres(){
        ResponseEntity<List<DistributionCentre>> response = restTemplate.exchange(
                distributionCentreApiUrl + "/distribution-centres",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DistributionCentre>>() {
                });
        return response.getBody();
    }
}