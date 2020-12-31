package com.alirnp.nationalcode;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ValidateNationalCodeTest {

    @Test
    public void validation1() {
        assertTrue(validationNationalCode("1810350832"));

    }

    @Test
    public void validation2() {
        assertFalse(validationNationalCode("5214526456"));

    }

    @Test
    public void validation3() {
        // rnp ♥

        validationNationalCode("0590267752");
    }

    @Test
    public void validation4() {
        assertFalse(NationalCode.validationNationalCode("7731689951"));
    }

    private static boolean validationNationalCode(String code){

        int length = 10 ;

        // check length
        if (code.length() != length)
            return false;

        // check exceptions
        if (NationalCode.specialCasesThatAreNotNationalCode(code))
            return false;

        long nationalCode = Long.parseLong(code);
        byte[] arrayNationalCode = new byte[length];

        //extract digits from number
        for (int i = 0; i < length ; i++) {
            arrayNationalCode[i] = (byte) (nationalCode % length);
            nationalCode = nationalCode / length;
        }

        // check the control digit
        int sum = 0;
        for (int i = 9; i > 0 ; i--)
            sum += arrayNationalCode[i] * (i+1);

        int temp = sum % 11;
        if (temp < 2)
            return arrayNationalCode[0] == temp;
        else
            return arrayNationalCode[0] == 11 - temp;
    }

    @Deprecated  //this method have a bug :)
    private static boolean validateCodeOLD(long code) {

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

}