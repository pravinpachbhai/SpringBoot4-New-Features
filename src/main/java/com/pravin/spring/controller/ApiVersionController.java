package com.pravin.spring.controller;

import com.pravin.spring.dto.CustomerDto;
import com.pravin.spring.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class ApiVersionController {

    private static final Logger log = LoggerFactory.getLogger(ApiVersionController.class);

    private final CustomerService customerService;
    public ApiVersionController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping(value = "/{id}", version = "1")
    @Operation(summary = "Get customer by ID", description = "Returns a single customer")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved customer")
    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<CustomerDto> getCustomerByIdV1(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerById(id));
    }


    @GetMapping(value = "/{id}", version = "2")
    @Operation(summary = "Get customer by ID", description = "Returns a single customer")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved customer")
    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<CustomerDto> getCustomerByIdV2(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerById(id));
    }

}
