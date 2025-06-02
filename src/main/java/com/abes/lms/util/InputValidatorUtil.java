package com.abes.lms.util;


import com.abes.lms.exception.*;

import java.util.regex.Pattern;

public class InputValidatorUtil {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%?&]{8,20}$"
    );

    public static void validate(String input) throws InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException("Input cannot be null or empty.");
        }
    }

    public static void validateEmail(String email) throws InvalidEmailException {
        validate(email);
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailException("Invalid email format.");
        }
    }
    public static void validatePassword(String password) throws InvalidPasswordFormatException {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new InvalidPasswordFormatException("Password must be 8-20 characters long and include uppercase, lowercase, digit, and special character.");
        }
    }


    public static void validateNumeric(String value) throws InvalidNumberException {
        validate(value);
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new InvalidNumberException("Input must be a numeric value.");
        }
    }

    public static void validateInteger(String value) throws InvalidIntegerException {
        validate(value);
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidIntegerException("Input must be an integer.");
        }
    }
}
