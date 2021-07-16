package com.openclassrooms.SafetyNetAlerts.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FireStationTest {

    private FireStation fireStationUnderTest;

    @BeforeEach
    void setUp() {
        fireStationUnderTest = new FireStation(List.of("value"), 0);
    }

    @Test
    void testAddAddresses() {
        fireStationUnderTest.setAddressList(List.of("address"));
    }

    @Test
    void testToString() {
        final String result = fireStationUnderTest.toString();
        assertEquals("FireStation [address=[value], station=0]", result);
    }

    @Test
    void testEqualsAndHashCode() {
        EqualsVerifier.simple().forClass(FireStation.class)
                .verify();
    }
}
