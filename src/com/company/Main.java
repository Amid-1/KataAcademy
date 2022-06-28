package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(calc(input));
    }

    public static String calc(String input) {
        if (input.matches(".*[^+\\-*/0-9IVX ].*")) {
            throw new IllegalArgumentException("Incorrect characters in input");
        }

        String[] parts = input.trim().split("[+\\-*/]");

        if (parts.length !=2) {
            throw new IllegalArgumentException("Incorrect input");
        }

        String firstValueString = parts[0].trim();
        String secondValueString = parts[1].trim();
        String operation = input.replaceFirst(parts[0],"").replaceFirst(parts[1],"");
        boolean isArabic = isArabicAllValues(firstValueString,secondValueString);

        int firstValue = parseValue(firstValueString, isArabic);
        int secondValue = parseValue(secondValueString, isArabic);

        if (firstValue < 1 || firstValue > 10 || secondValue < 1 || secondValue > 10) {
            throw new IllegalArgumentException("Need only from 1 to 10 numbers");
        }

        int result = 0;
        switch (operation) {
            case "-":
                result = firstValue - secondValue;
                break;
            case "+":
                result = firstValue + secondValue;
                break;
            case "*":
                result = firstValue * secondValue;
                break;
            case "/":
                result = firstValue / secondValue;
                break;
            default:
                throw new IllegalArgumentException("Incorrect operation");
        }

        if (result<1 && !isArabic) {
            throw new IllegalStateException("Result with roman number must be >=1");
        }

        return formatResult(result,isArabic);
    }

    private static boolean isArabicAllValues(String firstValueString, String secondValueString) {
        if (isArabic(firstValueString) == !isArabic(secondValueString)) {
            throw new IllegalArgumentException("Can't operate with arabic and roman numbers in one line");
        }

        return isArabic(firstValueString) && isArabic(secondValueString);
    }

    private static boolean isArabic(String valueString) {
        if (valueString.matches(".*[0-9].*")) {
            if (valueString.matches(".*[IVX].*")) {
                throw new IllegalArgumentException("You can't use arabic and roman digits in one number");
            }
            return true;
        } else {
            return false;
        }
    }

    private static int parseValue(String valueString, boolean isArabic) {
        if (isArabic) {
            return Integer.parseInt(valueString);
        } else {
            return RomanUtils.romanToArabic(valueString);
        }
    }

    private static String formatResult(int result, boolean isArabic) {
        if (isArabic) {
            return Integer.toString(result);
        } else {
            return RomanUtils.arabicToRoman(result);
        }
    }
}
