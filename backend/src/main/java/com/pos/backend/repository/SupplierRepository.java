package com.pos.backend.repository;

import com.pos.backend.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    // Custom query method to find suppliers by the ownedBy field
    List<Supplier> findByOwnedBy(String ownedBy);

    Optional<Supplier> findByCompanyName(String companyName);
    
}
