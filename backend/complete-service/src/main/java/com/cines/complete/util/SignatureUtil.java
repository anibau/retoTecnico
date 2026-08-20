package com.cines.complete.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class SignatureUtil {

    private static final Logger log = LoggerFactory.getLogger(SignatureUtil.class);

    private SignatureUtil() {
    }

    /**
     * Firma requerida por PayU: MD5(apiKey~merchantId~referenceCode~amount~currency).
     * El monto debe formatearse EXACTAMENTE igual aquí y en additionalValues.TX_VALUE.value.
     * Riesgo conocido: si el sandbox responde "firma inválida" al probar con tarjetas reales
     * de prueba, revisar si espera el monto sin decimales (ej. "25") en vez de "25.00" —
     * se deja logueado en DEBUG el string pre-MD5 para poder ajustarlo rápido.
     */
    public static String buildSignature(String apiKey, Long merchantId, String referenceCode,
                                         BigDecimal amount, String currency) {
        String amountStr = amount.setScale(2, RoundingMode.HALF_UP).toPlainString();
        String raw = apiKey + "~" + merchantId + "~" + referenceCode + "~" + amountStr + "~" + currency;
        log.debug("PayU pre-signature string: {}", raw);
        return md5(raw);
    }

    private static String md5(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 no disponible en esta JVM", e);
        }
    }
}
