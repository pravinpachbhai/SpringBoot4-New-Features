package com.pravin.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class MeController {

    @GetMapping(value = "/")
    public ResponseEntity<Map<String, String>> me(Principal principal) {
        return ResponseEntity.ok(Map.of("Name", principal.getName()));
    }
}
