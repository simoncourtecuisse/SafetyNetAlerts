package com.openclassrooms.SafetyNetAlerts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.openclassrooms.SafetyNetAlerts.JacksonConfiguration;
import com.openclassrooms.SafetyNetAlerts.dao.FireStationDaoImpl;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.swing.event.HyperlinkEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FireStationController.class)
@Import(JacksonConfiguration.class)
class FireStationControllerTest1 {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationDaoImpl mockFireStationDao;
    @MockBean
    private PersonDao mockPersonDao;

    private FireStation createFireStation() {
        List<String> anyAddress = new ArrayList<>();
        FireStation fireStation = new FireStation(anyAddress, 1);
        fireStation.setStation(1);
        return fireStation;

    }

    @Test
    void testAddFireStation() throws Exception {
        when(mockFireStationDao.savedFireStation(any(FireStation.class))).thenReturn(createFireStation());
        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                .content(new ObjectMapper().writeValueAsString(createFireStation()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                //.andExpect(jsonPath("$.address").value("[1]"))
                .andExpect(jsonPath("$[0].station").value(1));

    }

//    @Test
//    void testAddFireStation() throws Exception {
//        // Setup
//       when(mockFireStationDao.savedFireStation(new FireStation(List.of("value"), 0))).thenReturn(new FireStation(List.of("value"), 0));
//
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(post("/firestation")
//                .param("street", "2")
//                .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//
//        // Verify the results
//        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
//        assertEquals("expectedResponse", response.getContentAsString());
//    }
//    @Test
//    void testAddFireStation() throws Exception {
//        FireStation testFireStation = new FireStation((List.of("address")), 2);
//        final List<FireStation> fireStationsList = List.of(testFireStation);
//        when(mockFireStationDao.findAll()).thenReturn(fireStationsList);
//
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty(String.valueOf(List.of("address")), "street");
//        jsonObject.addProperty("station", 3);
//        final MockHttpServletResponse response = mockMvc.perform(post("/firestation")
//                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
//    }

    @Test
    void testUpdateFireStation() throws Exception {
        // Setup
        when(mockFireStationDao.savedFireStation(new FireStation(List.of("value"), 0))).thenReturn(new FireStation(List.of("value"), 0));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/firestation")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testDeleteFireStation() throws Exception {
        // Setup
        when(mockFireStationDao.deletedFireStation(new FireStation(List.of("value"), 0))).thenReturn(new FireStation(List.of("value"), 0));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/firestation")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testGetPersonByFireStation() throws Exception {
        // Setup

        // Configure PersonDao.getPersonFromSameStation(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(personList);

        when(mockFireStationDao.filterResult(any(String[].class), eq(List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"))))).thenReturn(new JsonArray(0));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/firestation")
                .param("station", "station")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testGetPersonByFireStation_PersonDaoReturnsNoItems() throws Exception {
        // Setup
        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(Collections.emptyList());
        when(mockFireStationDao.filterResult(any(String[].class), eq(List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"))))).thenReturn(new JsonArray(0));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/firestation")
                .param("station", "station")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testGetPersonByFireStation_FireStationDaoImplReturnsNoItems() throws Exception {
        // Setup

        // Configure PersonDao.getPersonFromSameStation(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(personList);

        when(mockFireStationDao.filterResult(any(String[].class), eq(List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"))))).thenReturn(new JsonArray());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/firestation")
                .param("station", "station")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testGetPersonByFireStation_FireStationDaoImplThrowsJsonProcessingException() throws Exception {
        // Setup

        // Configure PersonDao.getPersonFromSameStation(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(personList);

        when(mockFireStationDao.filterResult(any(String[].class), eq(List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"))))).thenThrow(JsonProcessingException.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/firestation")
                .param("station", "station")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testPhoneAlert() throws Exception {
        // Setup

        // Configure FireStationDaoImpl.findAll(...).
        final List<FireStation> fireStations = List.of(new FireStation(List.of("value"), 0));
        when(mockFireStationDao.findAll()).thenReturn(fireStations);

        // Configure PersonDao.getPersonFromSameStation(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/phoneAlert")
                .param("firestation", "firestation")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testPhoneAlert_FireStationDaoImplReturnsNoItems() throws Exception {
        // Setup
        when(mockFireStationDao.findAll()).thenReturn(Collections.emptyList());

        // Configure PersonDao.getPersonFromSameStation(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/phoneAlert")
                .param("firestation", "firestation")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testPhoneAlert_PersonDaoReturnsNoItems() throws Exception {
        // Setup

        // Configure FireStationDaoImpl.findAll(...).
        final List<FireStation> fireStations = List.of(new FireStation(List.of("value"), 0));
        when(mockFireStationDao.findAll()).thenReturn(fireStations);

        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/phoneAlert")
                .param("firestation", "firestation")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testPhoneAlert_ThrowsFileNotFoundException() throws Exception {
        // Setup

        // Configure FireStationDaoImpl.findAll(...).
        final List<FireStation> fireStations = List.of(new FireStation(List.of("value"), 0));
        when(mockFireStationDao.findAll()).thenReturn(fireStations);

        // Configure PersonDao.getPersonFromSameStation(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/phoneAlert")
                .param("firestation", "firestation")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testFlood() throws Exception {
        // Setup

        // Configure PersonDao.getPersonFromSameStation(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(personList);

        when(mockFireStationDao.filterResult(any(String[].class), eq(List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"))))).thenReturn(new JsonArray(0));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/flood")
                .param("stations", "stations")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testFlood_PersonDaoReturnsNoItems() throws Exception {
        // Setup
        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(Collections.emptyList());
        when(mockFireStationDao.filterResult(any(String[].class), eq(List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"))))).thenReturn(new JsonArray(0));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/flood")
                .param("stations", "stations")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testFlood_FireStationDaoImplReturnsNoItems() throws Exception {
        // Setup

        // Configure PersonDao.getPersonFromSameStation(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(personList);

        when(mockFireStationDao.filterResult(any(String[].class), eq(List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"))))).thenReturn(new JsonArray());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/flood")
                .param("stations", "stations")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testFlood_FireStationDaoImplThrowsJsonProcessingException() throws Exception {
        // Setup

        // Configure PersonDao.getPersonFromSameStation(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(personList);

        when(mockFireStationDao.filterResult(any(String[].class), eq(List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"))))).thenThrow(JsonProcessingException.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/flood")
                .param("stations", "stations")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testFire() throws Exception {
        // Setup

        // Configure PersonDao.findAll(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Configure FireStationDaoImpl.findAll(...).
        final List<FireStation> fireStations = List.of(new FireStation(List.of("value"), 0));
        when(mockFireStationDao.findAll()).thenReturn(fireStations);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/fire")
                .param("queryStringParameters", "queryStringParameters")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testFire_PersonDaoReturnsNoItems() throws Exception {
        // Setup
        when(mockPersonDao.findAll()).thenReturn(Collections.emptyList());

        // Configure FireStationDaoImpl.findAll(...).
        final List<FireStation> fireStations = List.of(new FireStation(List.of("value"), 0));
        when(mockFireStationDao.findAll()).thenReturn(fireStations);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/fire")
                .param("queryStringParameters", "queryStringParameters")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testFire_PersonDaoThrowsFileNotFoundException() throws Exception {
        // Setup
        when(mockPersonDao.findAll()).thenThrow(FileNotFoundException.class);

        // Configure FireStationDaoImpl.findAll(...).
        final List<FireStation> fireStations = List.of(new FireStation(List.of("value"), 0));
        when(mockFireStationDao.findAll()).thenReturn(fireStations);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/fire")
                .param("queryStringParameters", "queryStringParameters")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testFire_FireStationDaoImplReturnsNoItems() throws Exception {
        // Setup

        // Configure PersonDao.findAll(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        when(mockFireStationDao.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/fire")
                .param("queryStringParameters", "queryStringParameters")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }
}
