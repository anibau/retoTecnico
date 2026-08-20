package com.cines.candystore.repository;

import com.cines.candystore.dto.ProductDto;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductDto> findAllActive() {
        return jdbcTemplate.execute(
                (Connection con) -> con.prepareCall("{call sp_GetCandystoreProducts}"),
                (CallableStatementCallback<List<ProductDto>>) cs -> {
                    try (ResultSet rs = cs.executeQuery()) {
                        List<ProductDto> products = new ArrayList<>();
                        while (rs.next()) {
                            products.add(new ProductDto(
                                    rs.getLong("Id"),
                                    rs.getString("Name"),
                                    rs.getString("Description"),
                                    rs.getBigDecimal("Price"),
                                    rs.getString("Category")));
                        }
                        return products;
                    }
                });
    }
}
