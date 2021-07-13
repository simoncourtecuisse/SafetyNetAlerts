package com.openclassrooms.SafetyNetAlerts.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonTest {

    private Person personUnderTest;

    @BeforeEach
    void setUp() {
        personUnderTest = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
    }

    @Test
    void testAddMedications() {
        // Setup

        // Run the test
        personUnderTest.addMedications("medication");

        // Verify the results
    }

    @Test
    void testAddAllergies() {
        // Setup

        // Run the test
        personUnderTest.addAllergies("allergy");

        // Verify the results
    }

    @Test
    void testCalculateAge() {
        // Setup

        // Run the test
        final int result = personUnderTest.calculateAge(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1));

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    void testGetAge() {
        // Setup

        // Run the test
        final int result = personUnderTest.getAge();

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    void testEqualsAndHashCode() {
        EqualsVerifier.simple().forClass(Person.class)
                .withIgnoredFields("phone", "email", "medicalRecord", "birthdate")
                .verify();
    }

    @Test
    void testToString() {
        final String result = personUnderTest.toString();
        assertEquals("Person [FirstName = firstName, LastName = lastName, Birthdate = null, Age = 0, Location = Location [address=address, city=city, zip=0], Phone = phone, Email = email, Medications = [], Allergies = []]", result);
    }
}
