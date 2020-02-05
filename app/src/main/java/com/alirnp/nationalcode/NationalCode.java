package com.alirnp.nationalcode;

import java.util.Random;

class NationalCode {

    static boolean validateCode(long code) {

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
        } else {
            System.out.println("len :" + len);
        }


        return false;
    }

    static boolean validateCode(String code) {
        return validateCode(Long.parseLong(code));
    }

    static Long generateCode() {
        int code;
        int min = 111111111;
        int max = 999999999;
        Random r = new Random();
        code = r.nextInt((max - min) + 1) + min;

        int index = 2;
        int number, temp;
        int sum = 0;

        temp = code;
        while (temp > 0) {
            number = temp % 10;
            temp = temp / 10;
            sum += number * index;
            index++;
        }

        int securityCode;
        int left = sum % 11;

        if (left < 2)
            securityCode = left;
        else
            securityCode = 11 - left;

        String finalCode = String.valueOf(code) + securityCode;

        return Long.parseLong(finalCode);
    }
}
