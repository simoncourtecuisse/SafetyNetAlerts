package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.model.Location;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationDaoImplTest {

    @Mock
    private PersonDao mockPersonDao;

    @InjectMocks
    private LocationDaoImpl locationDaoImplUnderTest;

    @Test
    void testInitLocations() throws Exception {
        // Setup
        final List<Person> persons = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        final List<Location> expectedResult = List.of(new Location("address", "city", 0));

        // Configure PersonDao.findAll(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final List<Location> result = locationDaoImplUnderTest.initLocations(persons);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testInitLocations_PersonDaoReturnsNoItems() throws Exception {
        // Setup
        final List<Person> persons = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        final List<Location> expectedResult = List.of(new Location("address", "city", 0));
        when(mockPersonDao.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Location> result = locationDaoImplUnderTest.initLocations(persons);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testInitLocations_PersonDaoThrowsFileNotFoundException() throws Exception {
        // Setup
        final List<Person> persons = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenThrow(FileNotFoundException.class);

        // Run the test
        assertThrows(FileNotFoundException.class, () -> locationDaoImplUnderTest.initLocations(persons));
    }
}
