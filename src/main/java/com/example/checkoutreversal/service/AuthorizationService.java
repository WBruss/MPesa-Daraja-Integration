package com.example.checkoutreversal.service;

import com.example.checkoutreversal.data.ApiCreds;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author pwainaina
 */
@Service
@Slf4j
public class AuthorizationService {

    @Resource
    Environment environment;
    @Autowired
    RestTemplate restTemplate;

    public ApiCreds doGetAccessToken(){
        // Make authorization request
        String basicAuth = environment.getProperty("mpesa.checkout.ConsumerKey") +
                ":" +
                environment.getProperty("mpesa.checkout.ConsumerSecret");
        String basicAuthBase64 = "Basic " + Base64.getEncoder().encodeToString(basicAuth.getBytes(StandardCharsets.UTF_8));
        log.info("Basic Auth: " + basicAuth);
        log.info("Basic Auth: " + basicAuthBase64);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", basicAuthBase64);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "client_credentials");

        ResponseEntity<ApiCreds> apiCredsResponseEntity = restTemplate.exchange(
                Objects.requireNonNull(environment.getProperty("mpesa.checkout.AuthUrl")),
                HttpMethod.GET,
                httpEntity,
                ApiCreds.class
        );

        return apiCredsResponseEntity.getBody();
    }
}
