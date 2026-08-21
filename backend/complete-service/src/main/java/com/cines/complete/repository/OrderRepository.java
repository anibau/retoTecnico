package com.cines.complete.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Long createPendingOrder(String referenceCode, String email, String fullName,
                                    String documentType, String documentNumber,
                                    BigDecimal amount, String currency) {
        return jdbcTemplate.execute((java.sql.Connection con) ->
                        con.prepareCall("{call sp_CreatePendingOrder(?,?,?,?,?,?,?,?)}"),
                (CallableStatement cs) -> {
                    cs.setString(1, referenceCode);
                    cs.setString(2, email);
                    cs.setString(3, fullName);
                    cs.setString(4, documentType);
                    cs.setString(5, documentNumber);
                    cs.setBigDecimal(6, amount);
                    cs.setString(7, currency);
                    cs.registerOutParameter(8, Types.INTEGER);
                    cs.execute();
                    return (long) cs.getInt(8);
                });
    }

    public void addOrderItem(Long orderId, Long productId, String productName,
                              BigDecimal unitPrice, Integer quantity) {
        jdbcTemplate.execute((java.sql.Connection con) ->
                        con.prepareCall("{call sp_AddOrderItem(?,?,?,?,?)}"),
                (CallableStatement cs) -> {
                    cs.setLong(1, orderId);
                    cs.setLong(2, productId);
                    cs.setString(3, productName);
                    cs.setBigDecimal(4, unitPrice);
                    cs.setInt(5, quantity);
                    cs.execute();
                    return null;
                });
    }

    public void updateOrderPayUResult(String referenceCode, String payUTransactionId, Long payUOperationDate,
                                       String payUState, String payUResponseCode) {
        jdbcTemplate.execute((java.sql.Connection con) ->
                        con.prepareCall("{call sp_UpdateOrderPayUResult(?,?,?,?,?)}"),
                (CallableStatement cs) -> {
                    cs.setString(1, referenceCode);
                    cs.setString(2, payUTransactionId);
                    if (payUOperationDate != null) {
                        cs.setLong(3, payUOperationDate);
                    } else {
                        cs.setNull(3, Types.BIGINT);
                    }
                    cs.setString(4, payUState);
                    cs.setString(5, payUResponseCode);
                    cs.execute();
                    return null;
                });
    }

    public OrderRecord findByTransactionId(String transactionId) {
        return jdbcTemplate.execute((java.sql.Connection con) ->
                        con.prepareCall("{call sp_GetOrderByTransactionId(?)}"),
                (CallableStatement cs) -> {
                    cs.setString(1, transactionId);
                    boolean hasResults = cs.execute();
                    if (!hasResults) {
                        return null;
                    }
                    try (ResultSet rs = cs.getResultSet()) {
                        if (!rs.next()) {
                            return null;
                        }
                        OrderRecord record = new OrderRecord();
                        record.setId(rs.getLong("Id"));
                        record.setReferenceCode(rs.getString("ReferenceCode"));
                        record.setEmail(rs.getString("Email"));
                        record.setFullName(rs.getString("FullName"));
                        record.setDocumentNumber(rs.getString("DocumentNumber"));
                        record.setPayUState(rs.getString("PayUState"));
                        record.setStatus(rs.getString("Status"));
                        return record;
                    }
                });
    }

    public int completeOrder(String transactionId, String email, String documentNumber, Long operationDate) {
        return jdbcTemplate.execute((java.sql.Connection con) ->
                        con.prepareCall("{call sp_CompleteOrder(?,?,?,?,?)}"),
                (CallableStatement cs) -> {
                    cs.setString(1, transactionId);
                    cs.setString(2, email);
                    cs.setString(3, documentNumber);
                    if (operationDate != null) {
                        cs.setLong(4, operationDate);
                    } else {
                        cs.setNull(4, Types.BIGINT);
                    }
                    cs.registerOutParameter(5, Types.INTEGER);
                    cs.execute();
                    return cs.getInt(5);
                });
    }

    public static class OrderRecord {
        private Long id;
        private String referenceCode;
        private String email;
        private String fullName;
        private String documentNumber;
        private String payUState;
        private String status;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getReferenceCode() {
            return referenceCode;
        }

        public void setReferenceCode(String referenceCode) {
            this.referenceCode = referenceCode;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getDocumentNumber() {
            return documentNumber;
        }

        public void setDocumentNumber(String documentNumber) {
            this.documentNumber = documentNumber;
        }

        public String getPayUState() {
            return payUState;
        }

        public void setPayUState(String payUState) {
            this.payUState = payUState;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
