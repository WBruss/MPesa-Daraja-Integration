package com.example.checkoutreversal.service;

import com.example.checkoutreversal.data.CheckoutRequest;
import com.example.checkoutreversal.data.CheckoutAcknowledgement;
import com.example.checkoutreversal.data.MpesaCheckoutRequest;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Objects;

/**
 * @author pwainaina
 */
@Service
@Slf4j
public class CheckoutService {

    @Resource
    Environment environment;
    @Autowired
    RestTemplate restTemplate;

    public CheckoutAcknowledgement doCheckout(CheckoutRequest checkoutRequest){

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter format_hhmmss = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
        String YYYYMMDDhhmmss = localDateTime.format(format_hhmmss);

        String password = environment.getProperty("mpesa.checkout.BusinessShortCode") +
                environment.getProperty("mpesa.checkout.PassKey") +
                YYYYMMDDhhmmss;
        log.info("Clear password " + password);
        String encPassword = Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
        log.info("Enc Password " + encPassword);
        MpesaCheckoutRequest mpesaCheckoutRequest = new MpesaCheckoutRequest();
//        mpesaCheckoutRequest.setBusinessShortCode(Long.parseLong(Objects.requireNonNull(environment.getProperty("mpesa.checkout.BusinessShortCode"))));
        mpesaCheckoutRequest.setBusinessShortCode(174379);
        mpesaCheckoutRequest.setPassword(encPassword);
        mpesaCheckoutRequest.setTimestamp(YYYYMMDDhhmmss);
        mpesaCheckoutRequest.setAmount(checkoutRequest.getAmount());
        mpesaCheckoutRequest.setPartyA(checkoutRequest.getPhoneNumber());
        mpesaCheckoutRequest.setTransactionType(environment.getProperty("mpesa.checkout.TransactionType"));
        mpesaCheckoutRequest.setPartyB(174379L);
        mpesaCheckoutRequest.setPhoneNumber(checkoutRequest.getPhoneNumber());
        mpesaCheckoutRequest.setCallBackURL(checkoutRequest.getCallbackUrl());
        mpesaCheckoutRequest.setAccountReference(checkoutRequest.getAccountReference());
        mpesaCheckoutRequest.setTransactionDesc(checkoutRequest.getTransactionDesc());
        log.info("Mpesa Checkout Request: " + mpesaCheckoutRequest);

        String mpesaCheckoutRequestJson = new Gson().toJson(mpesaCheckoutRequest);

        // Make checkout request
        String basicAuth = environment.getProperty("mpesa.checkout.ConsumerKey") +
                ":" +
                environment.getProperty("mpesa.checkout.ConsumerSecret");
        String basicAuthBase64 = "Bearer " + "AHL5kC2nUz51SbonS8qpapmAKpiQ";
                //Base64.getEncoder().encodeToString(basicAuth.getBytes(StandardCharsets.UTF_8));
        log.info("Basic Auth: " + basicAuth);
        log.info("Basic Auth: " + basicAuthBase64);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", basicAuthBase64);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> httpEntity = new HttpEntity<>(mpesaCheckoutRequestJson, httpHeaders);

        ResponseEntity<String> acknowledgement = restTemplate.postForEntity(
                Objects.requireNonNull(environment.getProperty("mpesa.checkout.STKPushUrl")),
                httpEntity,
                String.class
        );

        log.info("Checkout Acknowledgement: \n" + acknowledgement);

        return new Gson().fromJson(acknowledgement.getBody(), CheckoutAcknowledgement.class);
    }


    public void doCheckoutCallback(String callback){
        log.info("Callback String: \n" + callback);
    }
}
