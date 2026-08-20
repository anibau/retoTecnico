package com.cines.complete.service;

import com.cines.common.exception.BusinessException;
import com.cines.complete.dto.CompleteRequest;
import com.cines.complete.dto.CompleteResponse;
import com.cines.complete.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderCompletionService {

    private final OrderRepository orderRepository;

    public OrderCompletionService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CompleteResponse complete(CompleteRequest request) {
        OrderRepository.OrderRecord order = orderRepository.findByTransactionId(request.getTransactionId());
        if (order == null) {
            throw new BusinessException("ORDER_NOT_FOUND", "No existe una orden para esa transacción");
        }

        boolean emailMatches = order.getEmail() != null && order.getEmail().equalsIgnoreCase(request.getEmail());
        boolean documentMatches = order.getDocumentNumber() != null
                && order.getDocumentNumber().equals(request.getDocumentNumber());
        boolean wasApproved = "APPROVED".equals(order.getPayUState());

        if (!emailMatches || !documentMatches || !wasApproved) {
            throw new BusinessException("ORDER_MISMATCH", "Los datos no coinciden con la transacción registrada");
        }

        int resultCode = orderRepository.completeOrder(request.getTransactionId(), request.getEmail(),
                request.getDocumentNumber(), request.getOperationDate());

        CompleteResponse response = new CompleteResponse();
        response.setResponseCode(resultCode == 0 ? "0" : String.valueOf(resultCode));
        response.setMessage(resultCode == 0 ? "Compra exitosa" : "No se pudo completar la orden");
        response.setOrderId(order.getId());
        return response;
    }
}
