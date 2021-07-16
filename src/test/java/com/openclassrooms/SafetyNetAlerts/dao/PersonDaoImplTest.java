package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PersonDaoImplTest {

    private PersonDaoImpl personDaoImplUnderTest;

    @BeforeEach
    void setUp() {
        personDaoImplUnderTest = new PersonDaoImpl();
        personDaoImplUnderTest.fireStationDao = mock(FireStationDaoImpl.class);
    }

    @Test
    void testInitPersons() throws Exception {
        // Setup
        final List<Person> expectedResult = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));

        // Run the test
        final List<Person> result = personDaoImplUnderTest.initPersons();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testInitPersons_ThrowsFileNotFoundException() {
        // Setup

        // Run the test
        assertThrows(FileNotFoundException.class, () -> personDaoImplUnderTest.initPersons());
    }

    @Test
    void testFindAll() {
        // Setup
        final List<Person> expectedResult = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));

        // Run the test
        final List<Person> result = personDaoImplUnderTest.findAll();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testSavedPerson() {
        // Setup
        final Person person = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        final Person expectedResult = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");

        // Run the test
        final Person result = personDaoImplUnderTest.savedPerson(person);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testDeletedPerson() {
        // Setup
        final Person person = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        final Person expectedResult = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");

        // Run the test
        final Person result = personDaoImplUnderTest.deletedPerson(person);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetPersonFromSameStation() {
        // Setup
        final List<Person> expectedResult = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));

        // Configure FireStationDaoImpl.getFireStationById(...).
        final List<FireStation> fireStations = List.of(new FireStation(List.of("value"), 0));
        when(personDaoImplUnderTest.fireStationDao.getFireStationById(0)).thenReturn(fireStations);

        // Run the test
        final List<Person> result = personDaoImplUnderTest.getPersonFromSameStation(0);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetPersonFromSameStation_FireStationDaoImplReturnsNoItems() {
        // Setup
        final List<Person> expectedResult = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(personDaoImplUnderTest.fireStationDao.getFireStationById(0)).thenReturn(Collections.emptyList());

        // Run the test
        final List<Person> result = personDaoImplUnderTest.getPersonFromSameStation(0);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetPersonEmail() {
        // Setup
        final List<Person> expectedResult = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));

        // Run the test
        final List<Person> result = personDaoImplUnderTest.getPersonEmail("city");

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
