package com.cines.complete.service;

import com.cines.complete.client.PayUClient;
import com.cines.complete.config.PayUProperties;
import com.cines.complete.dto.PaymentRequest;
import com.cines.complete.dto.payu.PayUSubmitTransactionRequest;
import com.cines.complete.dto.payu.PayUSubmitTransactionResponse;
import com.cines.complete.util.CardBinUtil;
import com.cines.complete.util.SignatureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayUGatewayService {

    private static final Logger log = LoggerFactory.getLogger(PayUGatewayService.class);

    private final PayUClient payUClient;
    private final PayUProperties properties;

    public PayUGatewayService(PayUClient payUClient, PayUProperties properties) {
        this.payUClient = payUClient;
        this.properties = properties;
    }

    public PayUSubmitTransactionResponse charge(PaymentRequest request, String referenceCode) {
        String currency = request.getCurrency() != null ? request.getCurrency() : "PEN";
        BigDecimal amount = request.getAmount().setScale(2, RoundingMode.HALF_UP);
        String signature = SignatureUtil.buildSignature(properties.getApiKey(), properties.getMerchantId(),
                referenceCode, amount, currency);

        PayUSubmitTransactionRequest payload = new PayUSubmitTransactionRequest();
        payload.test = properties.isTest();

        payload.merchant = new PayUSubmitTransactionRequest.Merchant();
        payload.merchant.apiLogin = properties.getApiLogin();
        payload.merchant.apiKey = properties.getApiKey();

        PayUSubmitTransactionRequest.Buyer buyer = new PayUSubmitTransactionRequest.Buyer();
        buyer.fullName = request.getFullName();
        buyer.emailAddress = request.getEmail();
        buyer.dniNumber = request.getDocumentNumber();

        PayUSubmitTransactionRequest.TxValue txValue = new PayUSubmitTransactionRequest.TxValue();
        txValue.value = amount;
        txValue.currency = currency;
        Map<String, PayUSubmitTransactionRequest.TxValue> additionalValues = new HashMap<>();
        additionalValues.put("TX_VALUE", txValue);

        PayUSubmitTransactionRequest.Order order = new PayUSubmitTransactionRequest.Order();
        order.accountId = properties.getAccountId();
        order.referenceCode = referenceCode;
        order.description = "Compra dulceria cine";
        order.signature = signature;
        order.additionalValues = additionalValues;
        order.buyer = buyer;

        PayUSubmitTransactionRequest.Payer payer = new PayUSubmitTransactionRequest.Payer();
        payer.fullName = request.getFullName();
        payer.emailAddress = request.getEmail();
        payer.dniNumber = request.getDocumentNumber();

        PayUSubmitTransactionRequest.CreditCard creditCard = new PayUSubmitTransactionRequest.CreditCard();
        creditCard.number = request.getCardNumber().replaceAll("\\s+", "");
        creditCard.securityCode = request.getCvv();
        creditCard.expirationDate = normalizeExpiration(request.getExpirationDate());
        creditCard.name = request.getCardHolderName();

        PayUSubmitTransactionRequest.Transaction transaction = new PayUSubmitTransactionRequest.Transaction();
        transaction.order = order;
        transaction.payer = payer;
        transaction.creditCard = creditCard;
        transaction.extraParameters = Collections.singletonMap("INSTALLMENTS_NUMBER", 1);
        transaction.type = "AUTHORIZATION_AND_CAPTURE";
        transaction.paymentMethod = CardBinUtil.detectPaymentMethod(creditCard.number);
        transaction.paymentCountry = properties.getPaymentCountry();

        payload.transaction = transaction;

        try {
            return payUClient.submitTransaction(payload);
        } catch (Exception e) {
            log.error("Error llamando a PayU sandbox: {}", e.getMessage(), e);
            PayUSubmitTransactionResponse errorResponse = new PayUSubmitTransactionResponse();
            errorResponse.code = "ERROR";
            errorResponse.error = e.getMessage();
            errorResponse.transactionResponse = new PayUSubmitTransactionResponse.TransactionResponse();
            errorResponse.transactionResponse.state = "ERROR";
            errorResponse.transactionResponse.responseMessage = e.getMessage();
            return errorResponse;
        }
    }

    /**
     * PayU espera la expiración como "yyyy/MM"; el frontend la envía como "MM/YY" o "MM/YYYY".
     */
    private String normalizeExpiration(String expirationDate) {
        String[] parts = expirationDate.split("/");
        String month = parts[0].trim();
        String year = parts[1].trim();
        if (year.length() == 2) {
            year = "20" + year;
        }
        return year + "/" + month;
    }
}
