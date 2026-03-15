package com.example.SpringProject.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.example.SpringProject.dao.ProductDto;
import com.example.SpringProject.dao.ProductUpsertRequest;
import com.example.SpringProject.entity.Category;
import com.example.SpringProject.entity.Product;
import com.example.SpringProject.entity.Supplier;
import com.example.SpringProject.repository.CategoryRepository;
import com.example.SpringProject.repository.ProductRepository;
import com.example.SpringProject.repository.SupplierRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
            SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }

    public ProductDto getProductById(Integer id) {
        return productRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    public List<ProductDto> getProductsByCategoryId(Integer categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ProductDto createProduct(ProductUpsertRequest request) {
        validateCreateRequest(request);

        Product product = new Product();
        product.setProductName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock_quantity(request.getStockQuantity() != null ? request.getStockQuantity() : 0);

        product.setCategory(getCategoryOrThrow(request.getCategoryId()));
        product.setSupplier(getSupplierOrThrow(request.getSupplierId()));

        Product saved = productRepository.save(product);
        return mapToDto(saved);
    }

    public ProductDto patchProduct(Integer id, ProductUpsertRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (request.getName() != null) {
            product.setProductName(request.getName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getStockQuantity() != null) {
            product.setStock_quantity(request.getStockQuantity());
        }
        if (request.getCategoryId() != null) {
            product.setCategory(getCategoryOrThrow(request.getCategoryId()));
        }
        if (request.getSupplierId() != null) {
            product.setSupplier(getSupplierOrThrow(request.getSupplierId()));
        }

        Product saved = productRepository.save(product);
        return mapToDto(saved);
    }

    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productRepository.deleteById(id);
    }

    private void validateCreateRequest(ProductUpsertRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "name is required");
        }
        if (request.getPrice() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "price is required");
        }
        if (request.getCategoryId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "categoryId is required");
        }
        if (request.getSupplierId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "supplierId is required");
        }
    }

    private Category getCategoryOrThrow(Integer categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        return category.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }

    private Supplier getSupplierOrThrow(Integer supplierId) {
        Optional<Supplier> supplier = supplierRepository.findById(supplierId);
        return supplier.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));
    }

    private ProductDto mapToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getProductName());
        productDto.setPrice(product.getPrice());

        Category category = product.getCategory();
        productDto.setCategoryName(category != null ? category.getName() : null);

        Supplier supplier = product.getSupplier();
        if (supplier != null) {
            productDto.setSupplierNames(List.of(supplier.getName()));
        } else {
            productDto.setSupplierNames(List.of());
        }

        return productDto;
    }
}