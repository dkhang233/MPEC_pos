package com.pos.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pos.backend.model.Customer;
import com.pos.backend.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getCustomers() {
        List<Customer> result = customerRepository.findAll();
        return result;
    }

    public Customer getCustomerById(int id) {
        Customer result = customerRepository.findById(id).orElse(null);
        return result;
    }

    public Customer createCustomer(Customer customer) {
        Customer result = customerRepository.save(customer);
        return result;
    }

    public Customer updateCustomer(int id, Customer customer) {
        Customer result = customerRepository.findById(id).orElse(null);
        if (result != null) {
            result.setAccountNumber(customer.getAccountNumber());
            result.setDiscount(customer.getDiscount());
            result.setDiscountType(customer.getDiscountType());
            result.setDeleted(customer.getDeleted());
            result.setDate(customer.getDate());
            result.setCreatedBy(customer.getCreatedBy());
            result.setConsent(customer.getConsent());
            result = customerRepository.save(result);
        }
        return result;
    }

}
