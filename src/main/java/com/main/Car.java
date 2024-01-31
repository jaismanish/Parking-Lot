package com.main;


import com.main.exception.InvalidRegistrationNumException;

public record Car(String registrationNumber, Color color) {
    public Car {
        String registrationNumberRegex = "^[A-Z]{2}\\d{2}[A-Z]{2}\\d{4}";

        if (!registrationNumber.matches(registrationNumberRegex)) {
            throw new InvalidRegistrationNumException("Invalid car registration number");
        }
    }
}
