package com.example.SpringProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringProject.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	List<Product> findByCategoryId(Integer categoryId);

	List<Product> findBySupplierId(Integer supplierId);

	List<Product> findByProductNameContainingIgnoreCase(String name);
}
