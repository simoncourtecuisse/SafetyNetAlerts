package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.controller.PersonController;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.service.JacksonConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@AutoConfigureMockMvc
//@ContextConfiguration(classes = {PersonController.class})
//@WebMvcTest(PersonController.class)
//@Import(JacksonConfiguration.class)
class PersonDaoImplTest {

    @InjectMocks
    private PersonDaoImpl personDaoImplUnderTest;

    @Mock
    private FireStationDaoImpl fireStationDaoImplUnderTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInitPersons() throws Exception {
        // Setup
        // final List<Person> expectedResult = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        //final List<Person> expectedResult = List.of(new Person("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"));
        final List<Person> expectedResult = personDaoImplUnderTest.initPersons();
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
//        final Person person = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
//        final Person expectedResult = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
//       List<Person> =
        Person person = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        personDaoImplUnderTest.savedPerson(person);
        //when(personDaoImplUnderTest.findAll()).thenReturn(personList);
        List<Person> personList = personDaoImplUnderTest.findAll();


        // Verify the results
        assertEquals(1, personList.size());
    }

    @Test
    void testDeletedPerson() {
        // Setup
        Person person = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personDaoImplUnderTest.setPersons(personList);

        // Run the test
        boolean result = personDaoImplUnderTest.deletedPerson1(person);


        // Verify the results
        assertEquals(true, result);
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