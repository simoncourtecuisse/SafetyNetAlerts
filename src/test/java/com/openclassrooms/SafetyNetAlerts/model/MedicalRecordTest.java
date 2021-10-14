package com.openclassrooms.SafetyNetAlerts.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MedicalRecordTest {

    private MedicalRecord medicalRecordUnderTest;

    @BeforeEach
    void setUp() {
        medicalRecordUnderTest = new MedicalRecord();
    }

    @Test
    void testAddMedications() {
        // Setup

        // Run the test
        medicalRecordUnderTest.addMedications("medication");

        // Verify the results
    }

    @Test
    void testAddAllergies() {
        // Setup

        // Run the test
        medicalRecordUnderTest.addAllergies("allergy");

        // Verify the results
    }

    @Test
    void testToString() {
        final String result = medicalRecordUnderTest.toString();
        assertEquals("medications = []" + ", allergies = []", result);
    }

    @Test
    void testEqualsAndHashCode() {
        EqualsVerifier.simple().forClass(MedicalRecord.class)
                .verify();
    }
}
