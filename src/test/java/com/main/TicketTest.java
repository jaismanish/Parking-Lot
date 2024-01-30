package com.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TicketTest {
    @Test
    void testTicketIsCreated() {
        Ticket ticket = new Ticket(5, 3, "AB12BC1234");

        assertNotNull(ticket);
    }
}

