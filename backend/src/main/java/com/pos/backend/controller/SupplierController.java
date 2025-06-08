package com.pos.backend.controller;

import com.pos.backend.model.Supplier;
import com.pos.backend.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Suppliers API", description = "API for managing suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping("/suppliers")
    @Operation(summary = "Get all items", description = "Retrieve a list of all suppliers in the system")
    public List<Supplier> getSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/suppliers/user")
    @Operation(summary = "Get all suppliers for specific user", description = "Retrieve a list of all suppliers in the system")
    public List<Supplier> getSuppliersByUsername(@RequestParam String username) {
        return supplierService.getSuppliersByUsername(username);
    }

    @GetMapping("/suppliers/{companyName}")
    @Operation(summary = "Get supplier by company name", description = "Retrieve a supplier by its company name")
    public Supplier getSupplierByCompanyName(@PathVariable String companyName) {
        return supplierService.getSupplierByCompanyName(companyName);
    }

    @PostMapping("/suppliers")
    @Operation(summary = "Create suppliers", description = "Create a new suppliers")
    public Supplier createItem(@RequestBody Supplier supplier) {
        return supplierService.createSupplier(supplier);
    }


    @PutMapping("/suppliers")
    @Operation(summary = "Update item by ID", description = "Update an item by its ID")
    public Supplier updateItem(@RequestBody Supplier supplier) {
        return supplierService.updateSupplier(supplier);
    }
//
//    @DeleteMapping("/suppliers/{id}")
//    @Operation(summary = "Delete item by ID", description = "Delete an item by its ID")
//    public void deleteItem(@PathVariable int id) {
//        supplierService.deleteItem(id);
//    }

   
}
