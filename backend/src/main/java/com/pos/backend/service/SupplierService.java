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

    public Supplier updateSupplier(Supplier supplier) {
        Supplier existingSupplier = supplierRepository.findById(supplier.getId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + supplier.getId()));
        existingSupplier.setCompanyName(supplier.getCompanyName());
        existingSupplier.setAddress(supplier.getAddress());
        existingSupplier.setPhoneNumber(supplier.getPhoneNumber());
        existingSupplier.setAddress(supplier.getAddress());
        existingSupplier.setDeleted(supplier.isDeleted());
        existingSupplier.setOwnedBy(supplier.getOwnedBy());

        existingSupplier = supplierRepository.save(existingSupplier);
        // Implementation for updating a supplier can be added here
        return existingSupplier; // Placeholder return statement
    }

    public Supplier getSupplierByCompanyName(String companyName) {
        return supplierRepository.findByCompanyName(companyName)
                .orElseThrow(() -> new RuntimeException("Supplier not found with company name: " + companyName));
    }
}
