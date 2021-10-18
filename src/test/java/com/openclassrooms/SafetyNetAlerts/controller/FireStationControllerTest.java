package com.openclassrooms.SafetyNetAlerts.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.openclassrooms.SafetyNetAlerts.JacksonConfiguration;
import com.openclassrooms.SafetyNetAlerts.dao.FireStationDaoImpl;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {FireStationController.class})
@WebMvcTest(FireStationController.class)
@Import(JacksonConfiguration.class)
class FireStationControllerTest {

    @Autowired
    private FireStationController fireStationController;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationDaoImpl mockFireStationDao;
    @MockBean
    private PersonDao mockPersonDao;

    @Test
    void testAddFireStation() throws Exception {
        // Setup
        FireStation testFireStation = new FireStation((List.of("addressList")), 2);
        final List<FireStation> fireStationsList = List.of(testFireStation);
        when(mockFireStationDao.savedFireStation(any(FireStation.class))).thenReturn(testFireStation);

        // Run the test
        String jsonObject = new Gson().toJson(testFireStation);
        final MockHttpServletResponse response = mockMvc.perform(post("/firestation")
                        .content(jsonObject).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
//        System.out.println(jsonObject);

        // Verify the results
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void testAddFireStation_NoContent() throws Exception {
        // Setup
        FireStation testFireStation = new FireStation((List.of("addressList")), 2);
        when(mockFireStationDao.savedFireStation(any(FireStation.class))).thenReturn(null);

        // Run the test
        String jsonObject = new Gson().toJson(testFireStation);
        final MockHttpServletResponse response = mockMvc.perform(post("/firestation")
                        .content(jsonObject).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
//        System.out.println(jsonObject);

        // Verify the results
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    void testUpdateFireStation() throws Exception {
        // Setup
        FireStation testFireStation = new FireStation((List.of("addressList")), 2);
        when(mockFireStationDao.savedFireStation(any(FireStation.class))).thenReturn(testFireStation);

        // Run the test
        String jsonObject = new Gson().toJson(testFireStation);
        final MockHttpServletResponse response = mockMvc.perform(put("/firestation")
                        .content(jsonObject).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
//        System.out.println(jsonObject);

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testUpdateFireStation_ReturnNotFound() throws Exception {
        // Setup
        FireStation testFireStation = new FireStation((List.of("addressList")), 2);
        when(mockFireStationDao.savedFireStation(any(FireStation.class))).thenReturn(null);

        // Run the test
        String jsonObject = new Gson().toJson(testFireStation);
        final MockHttpServletResponse response = mockMvc.perform(put("/firestation")
                        .content(jsonObject).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
//        System.out.println(jsonObject);

        // Verify the results
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("null", response.getContentAsString());
    }

    @Test
    void testDeleteFireStation() throws Exception {
        // Setup
        FireStation testFireStation = new FireStation((List.of("addressList")), 2);
        when(mockFireStationDao.deletedFireStation(any(FireStation.class))).thenReturn(testFireStation);

        // Run the test
        String jsonObject = new Gson().toJson(testFireStation);
        final MockHttpServletResponse response = mockMvc.perform(delete("/firestation")
                        .content(jsonObject).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testDeleteFireStation_ReturnNotFound() throws Exception {
        // Setup
        FireStation testFireStation = new FireStation((List.of("addressList")), 2);
        when(mockFireStationDao.deletedFireStation(any(FireStation.class))).thenReturn(null);

        // Run the test
        String jsonObject = new Gson().toJson(testFireStation);
        final MockHttpServletResponse response = mockMvc.perform(delete("/firestation")
                        .content(jsonObject).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("null", response.getContentAsString());
    }

    @Test
    void testGetPersonByFireStation() throws Exception {
        // Setup
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.getPersonFromSameStation(24)).thenReturn(personList);
        when(mockFireStationDao.filterResult(any(String[].class), eq(List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"))))).thenReturn(new JsonArray(0));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/firestation")
                        .param("station", "24")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"adults\":0,\"childs\":1,\"person\":[]}", response.getContentAsString());
    }

    @Test
    void testPhoneAlert() throws Exception {
        // Setup
        final List<FireStation> fireStations = List.of(new FireStation(List.of("value"), 0));
        when(mockFireStationDao.findAll()).thenReturn(fireStations);
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/phoneAlert")
                        .param("firestation", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[\"FirstName = firstName, LastName = lastName, Phone = phone\"]", response.getContentAsString());
    }

    @Test
    void testPhoneAlert_PersonDaoReturnsNoItems() throws Exception {
        // Setup
        final List<FireStation> fireStations = List.of(new FireStation(List.of("value"), 0));
        when(mockFireStationDao.findAll()).thenReturn(fireStations);
        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/phoneAlert")
                        .param("firestation", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("null", response.getContentAsString());
    }

    @Test
    void testFlood() throws Exception {
        // Setup
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(personList);
        when(mockFireStationDao.filterResult(any(String[].class), eq(List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"))))).thenReturn(new JsonArray(0));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/flood")
                        .param("stations", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"person\":[]}", response.getContentAsString());
    }

    @Test
    void testFlood_ReturnBadRequest() throws Exception {
        // Setup
        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(null);
        when(mockFireStationDao.filterResult(any(String[].class), eq(List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"))))).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/flood")
                        .param("stations", "stations")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("null", response.getContentAsString());
    }


    @Test
    void testFire() throws Exception {
        // Setup
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);
        final List<FireStation> fireStations = List.of(new FireStation(List.of("value"), 0));
        when(mockFireStationDao.findAll()).thenReturn(fireStations);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/fire")
                        .param("address", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testFire_PersonDaoReturnsNoItems() throws Exception {
        // Setup
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);
        final List<FireStation> fireStations = List.of(new FireStation(List.of("value"), 0));
        when(mockFireStationDao.findAll()).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/fire")
                        .param("address", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }
}
