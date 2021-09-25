package com.openclassrooms.SafetyNetAlerts.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FireStationDaoImplTest {

    private FireStationDaoImpl fireStationDaoImplUnderTest;

    @BeforeEach
    void setUp() {
        fireStationDaoImplUnderTest = new FireStationDaoImpl();
    }

    @Test
    void testGetFireStationById() {
        // Setup
        final List<FireStation> expectedResult = List.of(new FireStation(List.of("value"), 0));

        // Run the test
        final List<FireStation> result = fireStationDaoImplUnderTest.getFireStationById(0);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testInitFireStations() throws Exception {
        // Setup
        final List<FireStation> expectedResult = List.of(new FireStation(List.of("value"), 0));

        // Run the test
        final List<FireStation> result = fireStationDaoImplUnderTest.initFireStations();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testInitFireStations_ThrowsFileNotFoundException() {
        // Setup

        // Run the test
        assertThrows(FileNotFoundException.class, () -> fireStationDaoImplUnderTest.initFireStations());
    }

    @Test
    void testFindAll() {
        // Setup
        final List<FireStation> expectedResult = List.of(new FireStation(List.of("value"), 0));

        // Run the test
        final List<FireStation> result = fireStationDaoImplUnderTest.findAll();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testSavedFireStation() {
        // Setup
        final FireStation fireStation = new FireStation(List.of("value"), 0);
        final FireStation expectedResult = new FireStation(List.of("value"), 0);

        // Run the test
        final FireStation result = fireStationDaoImplUnderTest.savedFireStation(fireStation);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testDeletedFireStation() {
        // Setup
        final FireStation fireStation = new FireStation(List.of("value"), 0);
        final FireStation expectedResult = new FireStation(List.of("value"), 0);

        // Run the test
        final FireStation result = fireStationDaoImplUnderTest.deletedFireStation(fireStation);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testFilterResult() throws Exception {
        // Setup
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        final JsonArray expectedResult = new JsonArray(0);

        // Run the test
        final JsonArray result = fireStationDaoImplUnderTest.filterResult(new String[]{"value"}, personList);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testFilterResult_ThrowsJsonProcessingException() {
        // Setup
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));

        // Run the test
        assertThrows(JsonProcessingException.class, () -> fireStationDaoImplUnderTest.filterResult(new String[]{"value"}, personList));
    }
}
