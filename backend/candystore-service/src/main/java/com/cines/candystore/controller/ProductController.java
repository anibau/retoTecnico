package com.cines.candystore.controller;

import com.cines.candystore.dto.ProductDto;
import com.cines.candystore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candystore")
@Tag(name = "Candystore", description = "Productos de la dulcería")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    @Operation(summary = "Lista los productos de dulcería disponibles")
    public List<ProductDto> getProducts() {
        return productService.getProducts();
    }
}
