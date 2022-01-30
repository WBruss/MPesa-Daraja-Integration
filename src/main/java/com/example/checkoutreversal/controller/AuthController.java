package com.example.checkoutreversal.controller;

import com.example.checkoutreversal.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author pwainaina
 */
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    AuthorizationService authorizationService;

    @GetMapping("/access-token")
    public ResponseEntity<?> getAccessToken(){
        return ResponseEntity.ok(authorizationService.doGetAccessToken());
    }
}
