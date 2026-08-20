package com.cines.complete.util;

public final class CardBinUtil {

    private CardBinUtil() {
    }

    public static String detectPaymentMethod(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return "VISA";
        }
        char firstDigit = cardNumber.charAt(0);
        if (firstDigit == '5') {
            return "MASTERCARD";
        }
        return "VISA";
    }
}
