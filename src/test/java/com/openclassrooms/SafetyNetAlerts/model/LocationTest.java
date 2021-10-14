package com.openclassrooms.SafetyNetAlerts.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationTest {

    private Location locationUnderTest;

    @BeforeEach
    void setUp() {
        locationUnderTest = new Location("address", "city", 0);
    }

    @Test
    void testToString() {
        final String result = locationUnderTest.toString();
        assertEquals("Location [address=address" + ", city=city" + ", zip=0]", result);
    }

    @Test
    final void testEqualsAndHashCode() {
        EqualsVerifier.simple().forClass(Location.class)
                .verify();
    }

}
