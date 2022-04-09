package com.eria.EriaWebBackend.service;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class RestService {

  private final RestTemplate restTemplate;

  @Value("${spring.security.oauth2.client.registration.discord.client-id}")
  String clientID;
  @Value("${spring.security.oauth2.client.registration.discord.client-secret}")
  String clientSecret;
  @Value("${spring.security.oauth2.client.registration.discord.client-secret}")
  String grantType;
  public RestService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }
}
