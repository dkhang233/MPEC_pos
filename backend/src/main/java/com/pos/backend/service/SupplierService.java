package com.pos.backend.service;

import com.pos.backend.model.Item;
import com.pos.backend.model.Supplier;
import com.pos.backend.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public List<Supplier> getSuppliersByUsername(String username) {
        return supplierRepository.findByOwnedBy(username);
    }

    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier getSupplierByCompanyName(String companyName) {
        return supplierRepository.findByCompanyName(companyName)
                .orElseThrow(() -> new RuntimeException("Supplier not found with company name: " + companyName));
    }
}
