package com.cines.candystore.service;

import com.cines.candystore.dto.ProductDto;
import com.cines.candystore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getProducts() {
        return productRepository.findAllActive();
    }
}
