package com.cines.complete.dto.payu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayUSubmitTransactionResponse {

    public String code;
    public String error;
    public TransactionResponse transactionResponse;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TransactionResponse {
        public String state;
        public String responseCode;
        public String transactionId;
        public Long operationDate;
        public String authorizationCode;
        public String responseMessage;
    }
}
