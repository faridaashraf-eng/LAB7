/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package member2;

/**
 *
 * @author Jana
 */
public class Validator {

    public static String checkText(String text, String fieldName) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
        if (text.contains(",")) {
            throw new IllegalArgumentException(fieldName + " cannot contain a comma.");
        }
        return text.trim();
    }

    public static int checkPositiveInt(String text, String fieldName) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
        int number;
        try {
            number = Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a whole number.");
        }
        if (number <= 0) {
            throw new IllegalArgumentException(fieldName + " must be a positive number.");
        }
        return number;
    }

    public static double checkNonNegativeDouble(String text, String fieldName) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
        double number;
        try {
            number = Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a number.");
        }
        if (number < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative.");
        }
        return number;
    }
}
