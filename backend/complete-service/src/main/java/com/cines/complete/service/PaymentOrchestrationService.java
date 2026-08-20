package com.cines.complete.service;

import com.cines.complete.dto.PaymentRequest;
import com.cines.complete.dto.PaymentResponseDto;
import com.cines.complete.dto.payu.PayUSubmitTransactionResponse;
import com.cines.complete.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentOrchestrationService {

    private final OrderRepository orderRepository;
    private final PayUGatewayService payUGatewayService;

    public PaymentOrchestrationService(OrderRepository orderRepository, PayUGatewayService payUGatewayService) {
        this.orderRepository = orderRepository;
        this.payUGatewayService = payUGatewayService;
    }

    @Transactional
    public PaymentResponseDto pay(PaymentRequest request) {
        String referenceCode = "ORD-" + UUID.randomUUID();
        String currency = request.getCurrency() != null ? request.getCurrency() : "PEN";

        Long orderId = orderRepository.createPendingOrder(referenceCode, request.getEmail(), request.getFullName(),
                request.getDocumentType(), request.getDocumentNumber(), request.getAmount(), currency);

        request.getItems().forEach(item -> {
            String name = item.getProductName() != null ? item.getProductName() : "Producto #" + item.getProductId();
            orderRepository.addOrderItem(orderId, item.getProductId(), name, item.getUnitPrice(), item.getQuantity());
        });

        PayUSubmitTransactionResponse payUResponse = payUGatewayService.charge(request, referenceCode);
        PayUSubmitTransactionResponse.TransactionResponse tx = payUResponse.transactionResponse;

        String state = tx != null ? tx.state : "ERROR";
        String transactionId = tx != null ? tx.transactionId : null;
        Long operationDate = tx != null ? tx.operationDate : null;
        String responseCode = tx != null ? tx.responseCode : null;
        String responseMessage = tx != null ? tx.responseMessage : payUResponse.error;

        orderRepository.updateOrderPayUResult(referenceCode, transactionId, operationDate, state, responseCode);

        PaymentResponseDto response = new PaymentResponseDto();
        response.setApproved("APPROVED".equals(state));
        response.setState(state);
        response.setTransactionId(transactionId);
        response.setOperationDate(operationDate);
        response.setResponseMessage(responseMessage);
        response.setReferenceCode(referenceCode);
        return response;
    }
}
