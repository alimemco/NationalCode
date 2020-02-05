package com.alirnp.nationalcode;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ValidateNationalCodeTest {
    private static boolean validateCode(long code) {

        int len = (int) Math.log10(code) + 1;
        int sum = 0;
        int securityCode;
        long number;
        long temp;
        int lastNumber = 0;

        if (len == 10) {

            int index = 1;
            temp = code;

            while (temp > 0) {
                number = temp % 10;
                temp = temp / 10;

                if (index == 1) {
                    lastNumber = (int) number;
                } else {
                    sum += number * (index);
                }
                index++;
            }

            int left = sum % 11;

            securityCode = (left < 2) ? left : 11 - left;

            return securityCode == lastNumber;
        }


        return false;
    }

    private static boolean validateCode(String code) {
        return validateCode(Long.parseLong(code));
    }

    @Test
    public void validation() {
        assertTrue(validateCode("1810350832"));
        assertTrue(validateCode(1810350832L));

        assertFalse(validateCode("5214526456"));
        assertFalse(validateCode(5214526456L));
    }

}