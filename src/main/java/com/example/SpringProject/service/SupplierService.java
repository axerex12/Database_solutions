package com.example.SpringProject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.SpringProject.dao.SupplierDto;
import com.example.SpringProject.entity.Supplier;
import com.example.SpringProject.entity.SupplierAddress;
import com.example.SpringProject.repository.SupplierRepository;

@Service
public class SupplierService {
    private final SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    public SupplierDto getSupplierByEmail(String email) {
        Supplier supplier = repository.findByEmail(email);

        return mapToDto(supplier);
    }

    public List<SupplierDto> getAllSuppliers() {
        return repository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public SupplierDto getSupplierById(Integer id) {
        return repository.findById(id).map(this::mapToDto).orElse(null);
    }

    private SupplierDto mapToDto(Supplier supplier) {
        if (supplier == null) {
            return null;
        }

        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setId(supplier.getId());
        supplierDto.setEmail(supplier.getEmail());
        supplierDto.setName(supplier.getName());
        supplierDto.setPhone(supplier.getPhone());
        supplierDto.setContactName(supplier.getContactName());

        SupplierAddress supplierAddress = supplier.getSupplierAddress();

        supplierDto.setSupplierAddress(supplierAddress);

        return supplierDto;
    }
}
