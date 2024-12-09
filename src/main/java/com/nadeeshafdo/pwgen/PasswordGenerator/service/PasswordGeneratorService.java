package com.nadeeshafdo.pwgen.PasswordGenerator.service;

import com.nadeeshafdo.pwgen.PasswordGenerator.model.PasswordRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(PasswordGeneratorService.class);

    // Define character sets
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_+=<>?";

    // SecureRandom for generating random numbers
    private static final SecureRandom random = new SecureRandom();

    // Method to generate a random password
    public String generatePassword(PasswordRequest request) {
        int length = request.getLength();
        boolean includeUppercase = request.isIncludeUppercase();
        boolean includeLowercase = request.isIncludeLowercase();
        boolean includeDigits = request.isIncludeDigits();
        boolean includeSpecialCharacters = request.isIncludeSpecialCharacters();

        if (length < 1) {
            logger.error("Password length must be at least 1");
            throw new IllegalArgumentException("Password length must be at least 1");
        }

        StringBuilder characterSet = new StringBuilder();
        if (includeUppercase) characterSet.append(UPPERCASE);
        if (includeLowercase) characterSet.append(LOWERCASE);
        if (includeDigits) characterSet.append(DIGITS);
        if (includeSpecialCharacters) characterSet.append(SPECIAL_CHARACTERS);

        if (characterSet.length() == 0) {
            logger.error("At least one character set must be selected");
            throw new IllegalArgumentException("At least one character set must be selected");
        }

        StringBuilder password = new StringBuilder(length);

        // Ensure the password contains at least one character from each selected set
        if (includeUppercase) password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        if (includeLowercase) password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        if (includeDigits) password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        if (includeSpecialCharacters) password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        // Fill the rest of the password length with random characters from the selected sets
        for (int i = password.length(); i < length; i++) {
            password.append(characterSet.charAt(random.nextInt(characterSet.length())));
        }

        // Shuffle the password to ensure randomness
        String finalPassword = shuffleString(password.toString());

        // Validate password strength
        if (!isStrongPassword(finalPassword, request)) {
            logger.error("Generated password does not meet strength requirements");
            throw new IllegalArgumentException("Generated password does not meet strength requirements");
        }

        return finalPassword;
    }

    // Method to shuffle a string
    private String shuffleString(String input) {
        char[] characters = input.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = random.nextInt(characters.length);
            // Swap characters
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);
    }

    // Method to validate password strength
    private boolean isStrongPassword(String password, PasswordRequest request) {
        boolean hasUppercase = request.isIncludeUppercase() && password.matches(".*[A-Z].*");
        boolean hasLowercase = request.isIncludeLowercase() && password.matches(".*[a-z].*");
        boolean hasDigits = request.isIncludeDigits() && password.matches(".*\\d.*");
        boolean hasSpecialCharacters = request.isIncludeSpecialCharacters() && password.matches(".*[!@#$%^&*()-_+=<>?].*");

        return hasUppercase && hasLowercase && hasDigits && hasSpecialCharacters;
    }
}