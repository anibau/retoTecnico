package com.cines.candystore.service;

import com.cines.candystore.dto.ProductDto;
import com.cines.candystore.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Test
    void getProducts_returnsWhatRepositoryProvides() {
        List<ProductDto> products = List.of(
                new ProductDto(1L, "Canchita Mediana", "Canchita salada", new BigDecimal("12.90"), "Snacks"),
                new ProductDto(2L, "Gaseosa Grande", "Bebida gaseosa 32oz", new BigDecimal("9.90"), "Bebidas"));
        when(productRepository.findAllActive()).thenReturn(products);

        ProductService productService = new ProductService(productRepository);
        List<ProductDto> result = productService.getProducts();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Canchita Mediana");
        assertThat(result.get(0).getPrice()).isEqualByComparingTo("12.90");
        assertThat(result.get(1).getCategory()).isEqualTo("Bebidas");
        verify(productRepository).findAllActive();
    }

    @Test
    void getProducts_returnsEmptyListWhenNoActiveProducts() {
        when(productRepository.findAllActive()).thenReturn(Collections.emptyList());

        ProductService productService = new ProductService(productRepository);

        assertThat(productService.getProducts()).isEmpty();
    }
}
