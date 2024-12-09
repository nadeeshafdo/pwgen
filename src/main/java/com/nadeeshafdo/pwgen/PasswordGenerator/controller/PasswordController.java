package com.nadeeshafdo.pwgen.PasswordGenerator.controller;

import com.nadeeshafdo.pwgen.PasswordGenerator.exception.InvalidPasswordRequestException;
import com.nadeeshafdo.pwgen.PasswordGenerator.model.PasswordRequest;
import com.nadeeshafdo.pwgen.PasswordGenerator.service.PasswordGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Password Generator", description = "API for generating passwords")
public class PasswordController {

    private static final Logger logger = LoggerFactory.getLogger(PasswordController.class);

    @Autowired
    private PasswordGeneratorService passwordGeneratorService;

    @Operation(summary = "Generate a password")
    @GetMapping("/generate-password")
    public ResponseEntity<String> generatePassword(
            @Parameter(description = "Length of the password", required = true) @RequestParam int length,
            @Parameter(description = "Include uppercase letters", required = true) @RequestParam boolean includeUppercase,
            @Parameter(description = "Include lowercase letters", required = true) @RequestParam boolean includeLowercase,
            @Parameter(description = "Include digits", required = true) @RequestParam boolean includeDigits,
            @Parameter(description = "Include special characters", required = true) @RequestParam boolean includeSpecialCharacters) {

        PasswordRequest request = new PasswordRequest(length, includeUppercase, includeLowercase, includeDigits, includeSpecialCharacters);

        try {
            String password = passwordGeneratorService.generatePassword(request);
            logger.info("Generated password: {}", password);
            return ResponseEntity.ok(password);
        } catch (IllegalArgumentException e) {
            logger.error("Error generating password: {}", e.getMessage());
            throw new InvalidPasswordRequestException(e.getMessage());
        }
    }
}