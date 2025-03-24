package com.pos.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pos.backend.model.Customer;
import com.pos.backend.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@Tag(name = "Customer API", description = "API for managing customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/customers")
    @Operation(summary = "Get all customers", description = "Retrieve a list of all customers in the system")
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/customers/{id}")
    @Operation(summary = "Get customer by ID", description = "Retrieve a customer by its ID")
    public Customer getCustomerById(@RequestParam int id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/customers")
    @Operation(summary = "Create customer", description = "Create a new customer")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/customers/{id}")
    @Operation(summary = "Update customer by ID", description = "Update a customer by its ID")
    public Customer updateCustomer(@RequestParam int id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

}
