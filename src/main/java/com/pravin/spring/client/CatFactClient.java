package com.pravin.spring.client;

import com.pravin.spring.dto.CatFact;
import com.pravin.spring.dto.CatFacts;
import org.springframework.web.service.annotation.GetExchange;

public interface CatFactClient {
    @GetExchange("https://catfact.ninja/fact")
    CatFact fact();
}
