package com.example.checkoutreversal.controller;

import com.example.checkoutreversal.data.CheckoutRequest;
import com.example.checkoutreversal.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pwainaina
 */
@RestController
@RequestMapping("api/v1/checkout")
public class CheckoutSTKPush {

    @Autowired
    CheckoutService checkoutService;

    @PostMapping("/request")
    public ResponseEntity<?> checkoutRequest(@RequestBody CheckoutRequest checkoutRequest){
        return ResponseEntity.ok(checkoutService.doCheckout(checkoutRequest));
    }

    @PostMapping("/callback")
    public ResponseEntity<?> checkoutCallback(@RequestBody String callback){
        checkoutService.doCheckoutCallback(callback);
        return ResponseEntity.ok("Received");
    }
}
