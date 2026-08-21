package com.cines.complete.dto.payu;

import java.math.BigDecimal;
import java.util.Map;


public class PayUSubmitTransactionRequest {

    public String language = "es";
    public String command = "SUBMIT_TRANSACTION";
    public Merchant merchant;
    public Transaction transaction;
    public boolean test;

    public static class Merchant {
        public String apiLogin;
        public String apiKey;
    }

    public static class Transaction {
        public Order order;
        public Payer payer;
        public CreditCard creditCard;
        public Map<String, Object> extraParameters;
        public String type;
        public String paymentMethod;
        public String paymentCountry;
    }

    public static class Order {
        public Long accountId;
        public String referenceCode;
        public String description;
        public String language = "es";
        public String signature;
        public String notifyUrl = "";
        public Map<String, TxValue> additionalValues;
        public Buyer buyer;
    }

    public static class Buyer {
        public String fullName;
        public String emailAddress;
        public String dniNumber;
    }

    public static class Payer {
        public String fullName;
        public String emailAddress;
        public String dniNumber;
    }

    public static class TxValue {
        public BigDecimal value;
        public String currency;
    }

    public static class CreditCard {
        public String number;
        public String securityCode;
        public String expirationDate;
        public String name;
    }
}
