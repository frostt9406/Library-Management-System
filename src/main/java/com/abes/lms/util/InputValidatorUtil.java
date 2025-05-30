package com.abes.lms.util;


import com.abes.lms.exception.InvalidEmailException;
import com.abes.lms.exception.InvalidInputException;
import com.abes.lms.exception.InvalidIntegerException;
import com.abes.lms.exception.InvalidNumberException;

import java.util.regex.Pattern;

public class InputValidatorUtil {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

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
