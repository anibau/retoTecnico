package com.cines.complete.controller;

import com.cines.complete.dto.PaymentRequest;
import com.cines.complete.dto.PaymentResponseDto;
import com.cines.complete.service.PaymentOrchestrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Payments", description = "Orquesta el cobro con PayU sandbox")
public class PaymentController {

    private final PaymentOrchestrationService orchestrationService;

    public PaymentController(PaymentOrchestrationService orchestrationService) {
        this.orchestrationService = orchestrationService;
    }

    @PostMapping
    @Operation(summary = "Procesa un pago con tarjeta a través de PayU sandbox")
    public ResponseEntity<PaymentResponseDto> pay(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(orchestrationService.pay(request));
    }
}
