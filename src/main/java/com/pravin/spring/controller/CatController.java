package com.pravin.spring.controller;

import com.pravin.spring.client.CatFactClient;
import com.pravin.spring.dto.CatFact;
import org.springframework.http.ResponseEntity;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class CatController {
    private final CatFactClient catFactClient;
    private final AtomicInteger counter =  new AtomicInteger(0);
    public CatController(CatFactClient catFactClient){
        this.catFactClient = catFactClient;
    }

    @GetMapping(value="/cat/fact", version = "1")
    public ResponseEntity<CatFact> fact(){
      return ResponseEntity.ok(this.catFactClient.fact());
    }

    @ConcurrencyLimit(10)
    @Retryable(maxRetries = 5, includes = IllegalStateException.class)
    @GetMapping(value="/cat/factRetry", version = "1")
    public ResponseEntity<CatFact> factRetry(){
        if (this.counter.incrementAndGet() < 5) {
            IO.println("Service is not working!!!!! We will retry 5 times......");
            throw new IllegalStateException("No Cat Facts");
        }
        IO.println("Service is  working!!!!! ");
        return ResponseEntity.ok(this.catFactClient.fact());
    }

}
