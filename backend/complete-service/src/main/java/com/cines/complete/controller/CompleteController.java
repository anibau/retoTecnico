package com.cines.complete.controller;

import com.cines.complete.dto.CompleteRequest;
import com.cines.complete.dto.CompleteResponse;
import com.cines.complete.service.OrderCompletionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/complete")
@Tag(name = "Complete", description = "Finaliza la transacción luego de la confirmación de PayU")
public class CompleteController {

    private final OrderCompletionService completionService;

    public CompleteController(OrderCompletionService completionService) {
        this.completionService = completionService;
    }

    @PostMapping
    @Operation(summary = "Confirma y cierra la orden luego del pago aprobado por PayU")
    public ResponseEntity<CompleteResponse> complete(@Valid @RequestBody CompleteRequest request) {
        return ResponseEntity.ok(completionService.complete(request));
    }
}
