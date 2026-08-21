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
