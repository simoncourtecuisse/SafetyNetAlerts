//package com.openclassrooms.SafetyNetAlerts.dao;
//
//import com.openclassrooms.SafetyNetAlerts.JacksonConfiguration;
//import com.openclassrooms.SafetyNetAlerts.controller.PersonController;
//import com.openclassrooms.SafetyNetAlerts.model.FireStation;
//import com.openclassrooms.SafetyNetAlerts.model.Person;
//import org.junit.Assert;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.io.FileNotFoundException;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//@AutoConfigureMockMvc
//@ContextConfiguration(classes = {PersonController.class})
//@WebMvcTest(PersonController.class)
//@Import(JacksonConfiguration.class)
//class PersonDaoImplTest1 {
//
//    @Autowired
//    private PersonController personController;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private PersonDaoImpl personDaoImplUnderTest;
//
//    @MockBean
//    private MedicalRecordDao mockMedicalRecordDao;
//
////    @BeforeEach
////    void setUp() {
////        personDaoImplUnderTest = new PersonDaoImpl();
////        personDaoImplUnderTest.fireStationDao = mock(FireStationDaoImpl.class);
////    }
//
//    @Test
//    void testInitPersons() throws Exception {
//        // Setup
//        // final List<Person> expectedResult = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
//        //final List<Person> expectedResult = List.of(new Person("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"));
//        final List<Person> expectedResult = personDaoImplUnderTest.initPersons();
//        // Run the test
//        final List<Person> result = personDaoImplUnderTest.initPersons();
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testInitPersons_ThrowsFileNotFoundException() {
//        // Setup
//
//        // Run the test
//        assertThrows(FileNotFoundException.class, () -> personDaoImplUnderTest.initPersons());
//    }
//
//    @Test
//    void testFindAll() {
//        // Setup
//        final List<Person> expectedResult = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
//
//        // Run the test
//        final List<Person> result = personDaoImplUnderTest.findAll();
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
////    @Test
////    void testSavedPerson() {
////        // Setup
////        final Person person = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
////        final Person expectedResult = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
////
////        // Run the test
////        final Person result = personDaoImplUnderTest.savedPerson(person);
////
////        // Verify the results
////        assertEquals(expectedResult, result);
////    }
//
//    @Test
//    @Rollback(true)
//    void testSavedPerson(){
//        Person person = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
//        personDaoImplUnderTest.savedPerson(person);
//        List<Person> personList = List.of(person);
//        when(personDaoImplUnderTest.findAll()).thenReturn(personList);
//
//        Assert.assertEquals(1, personList.size());
//    }
//    @Test
//    void testDeletedPerson() {
//        // Setup
//        final Person person = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
//        final boolean expectedResult = true;
//
//        // Run the test
//        final boolean result = personDaoImplUnderTest.deletedPerson1(person);
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testGetPersonFromSameStation() {
//        // Setup
//        final List<Person> expectedResult = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
//
//        // Configure FireStationDaoImpl.getFireStationById(...).
//        final List<FireStation> fireStations = List.of(new FireStation(List.of("value"), 0));
//        when(personDaoImplUnderTest.fireStationDao.getFireStationById(0)).thenReturn(fireStations);
//
//        // Run the test
//        final List<Person> result = personDaoImplUnderTest.getPersonFromSameStation(0);
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testGetPersonFromSameStation_FireStationDaoImplReturnsNoItems() {
//        // Setup
//        final List<Person> expectedResult = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
//        when(personDaoImplUnderTest.fireStationDao.getFireStationById(0)).thenReturn(Collections.emptyList());
//
//        // Run the test
//        final List<Person> result = personDaoImplUnderTest.getPersonFromSameStation(0);
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testGetPersonEmail() throws Exception {
//        // Setup
//        final List<Person> expectedResult = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
//        when(personDaoImplUnderTest.findAll()).thenReturn(expectedResult);
//
//        final MockHttpServletResponse response = mockMvc.perform(get("/communityEmail")
//                        .param("city", "city")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
////        // Run the test
////        final List<Person> result = personDaoImplUnderTest.getPersonEmail("city");
//
//        // Verify the results
//        assertEquals(expectedResult, response);
//    }
//}
