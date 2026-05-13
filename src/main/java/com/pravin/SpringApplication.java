package com.pravin;

import com.pravin.spring.client.CatFactClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.service.registry.ImportHttpServices;


@ImportHttpServices(CatFactClient.class)
@SpringBootApplication
public class SpringApplication {
/*
http://localhost:8080/login
 http://localhost:8080/api-docs
 http://localhost:8080/swagger-ui/index.html
 http://localhost:8080/actuator/health
 http://localhost:8080/h2-console
 GET /api/customers?page=0&size=10&sort=name,asc
 GET /api/customers?sort=city,desc

*/


	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
	}

}
