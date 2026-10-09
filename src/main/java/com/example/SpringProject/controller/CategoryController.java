package com.example.SpringProject.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.SpringProject.dao.ProductDto;
import com.example.SpringProject.entity.Category;
import com.example.SpringProject.entity.Product;
import com.example.SpringProject.repository.CategoryRepository;
import com.example.SpringProject.service.ProductService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository repository;
    private final ProductService productService;

    public CategoryController(CategoryRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {

        if (category.getProducts() != null) {
            for (Product p : category.getProducts()) {
                p.setCategory(category);
            }
        }

        Category savedCategory = repository.save(category);

        return ResponseEntity.ok(savedCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody Category request) {
        return repository.findById(id)
                .map(category -> {
                    category.setName(request.getName());
                    return ResponseEntity.ok(repository.save(category));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDto>> getCategoryProducts(@PathVariable Integer id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productService.getProductsByCategoryId(id));
    }
}