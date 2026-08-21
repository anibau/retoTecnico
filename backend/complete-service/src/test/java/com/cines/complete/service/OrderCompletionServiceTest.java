package com.cines.complete.service;

import com.cines.common.exception.BusinessException;
import com.cines.complete.dto.CompleteRequest;
import com.cines.complete.dto.CompleteResponse;
import com.cines.complete.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderCompletionServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private CompleteRequest requestFor(String email, String documentNumber) {
        CompleteRequest request = new CompleteRequest();
        request.setEmail(email);
        request.setFullName("Juan Perez");
        request.setDocumentNumber(documentNumber);
        request.setOperationDate(1_700_000_000L);
        request.setTransactionId("tx-123");
        return request;
    }

    private OrderRepository.OrderRecord approvedOrder(String email, String documentNumber) {
        OrderRepository.OrderRecord order = new OrderRepository.OrderRecord();
        order.setId(1L);
        order.setEmail(email);
        order.setDocumentNumber(documentNumber);
        order.setPayUState("APPROVED");
        return order;
    }

    @Test
    void complete_returnsResponseCodeZero_whenOrderMatchesAndPayUApproved() {
        when(orderRepository.findByTransactionId("tx-123"))
                .thenReturn(approvedOrder("test@example.com", "12345678"));
        when(orderRepository.completeOrder(anyString(), anyString(), anyString(), any())).thenReturn(0);

        OrderCompletionService service = new OrderCompletionService(orderRepository);
        CompleteResponse response = service.complete(requestFor("test@example.com", "12345678"));

        assertThat(response.getResponseCode()).isEqualTo("0");
        assertThat(response.getMessage()).isEqualTo("Compra exitosa");
        assertThat(response.getOrderId()).isEqualTo(1L);
    }

    @Test
    void complete_throwsBusinessException_whenNoOrderExistsForTransaction() {
        when(orderRepository.findByTransactionId("tx-123")).thenReturn(null);

        OrderCompletionService service = new OrderCompletionService(orderRepository);

        assertThatThrownBy(() -> service.complete(requestFor("test@example.com", "12345678")))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("No existe una orden");
    }

    @Test
    void complete_throwsBusinessException_whenEmailDoesNotMatchStoredOrder() {
        when(orderRepository.findByTransactionId("tx-123"))
                .thenReturn(approvedOrder("original@example.com", "12345678"));

        OrderCompletionService service = new OrderCompletionService(orderRepository);

        assertThatThrownBy(() -> service.complete(requestFor("otro@example.com", "12345678")))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("no coinciden");
    }

    @Test
    void complete_throwsBusinessException_whenPayUWasNotApproved() {
        OrderRepository.OrderRecord pendingOrder = approvedOrder("test@example.com", "12345678");
        pendingOrder.setPayUState("DECLINED");
        when(orderRepository.findByTransactionId("tx-123")).thenReturn(pendingOrder);

        OrderCompletionService service = new OrderCompletionService(orderRepository);

        assertThatThrownBy(() -> service.complete(requestFor("test@example.com", "12345678")))
                .isInstanceOf(BusinessException.class);
    }
}
