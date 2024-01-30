package com.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarTest {
    @Test
    void testCarIsCreated() {
        Car car = new Car("TN12AB1234", Color.BLACK);
        assertNotNull(car);
    }

    @Test
    void testInvalidRegistrationCarThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Car("12CD", Color.RED));
    }
}
