package com.cines.complete.client;

import com.cines.complete.config.PayUProperties;
import com.cines.complete.dto.payu.PayUSubmitTransactionRequest;
import com.cines.complete.dto.payu.PayUSubmitTransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class PayUClient {

    private static final Logger log = LoggerFactory.getLogger(PayUClient.class);

    private final RestTemplate restTemplate;
    private final String endpoint;

    public PayUClient(RestTemplate payURestTemplate, PayUProperties properties) {
        this.restTemplate = payURestTemplate;
        this.endpoint = properties.getEndpoint();
    }

    public PayUSubmitTransactionResponse submitTransaction(PayUSubmitTransactionRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<PayUSubmitTransactionRequest> entity = new HttpEntity<>(request, headers);
        log.info("Enviando transacción a PayU sandbox, referenceCode={}", request.transaction.order.referenceCode);
        return restTemplate.postForObject(endpoint, entity, PayUSubmitTransactionResponse.class);
    }
}
