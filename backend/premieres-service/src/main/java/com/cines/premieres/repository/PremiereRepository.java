package com.cines.premieres.repository;

import com.cines.premieres.dto.PremiereDto;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PremiereRepository {

    private final JdbcTemplate jdbcTemplate;

    public PremiereRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PremiereDto> findAllActive() {
        return jdbcTemplate.execute(
                (Connection con) -> con.prepareCall("{call sp_GetPremieres}"),
                (CallableStatementCallback<List<PremiereDto>>) cs -> {
                    try (ResultSet rs = cs.executeQuery()) {
                        List<PremiereDto> premieres = new ArrayList<>();
                        while (rs.next()) {
                            premieres.add(new PremiereDto(
                                    rs.getLong("Id"),
                                    rs.getString("Title"),
                                    rs.getString("Description"),
                                    rs.getString("ImageUrl"),
                                    rs.getInt("DisplayOrder")));
                        }
                        return premieres;
                    }
                });
    }
}
