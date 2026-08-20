package com.cines.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Error de negocio esperado (ej. la orden no coincide con la transacción de PayU).
 * Se distingue de un error técnico para que el frontend pueda mostrar el mensaje tal cual.
 */
public class BusinessException extends ApiException {

    public BusinessException(String code, String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, code, message);
    }
}
