package com.openclassrooms.SafetyNetAlerts.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.openclassrooms.SafetyNetAlerts.JacksonConfiguration;
import com.openclassrooms.SafetyNetAlerts.dao.FireStationDaoImpl;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.json.Json;
import javax.swing.event.HyperlinkEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {FireStationController.class})
//@ExtendWith(SpringExtension.class)
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

//    @Test
//    void testAddFireStation() throws Exception {
//        // Setup
//       when(mockFireStationDao.savedFireStation(new FireStation(List.of("address"), 2))).thenReturn(new FireStation(List.of("address"), 2));
//
//        // Run the test
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty(String.valueOf(List.of("address")), "street");
//       // jsonObject.addProperty("83 street", "13");
//        System.out.println(jsonObject);
//        final MockHttpServletResponse response = mockMvc.perform(post("/firestation")
//                //.param("street", "2")
//                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//
//        // Verify the results
//        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
//        assertEquals("expectedResponse", response.getContentAsString());
//    }
    @Test
    void testAddFireStation() throws Exception {
       // FireStation testFtr = new FireStation(List.of("street"), 12);
        FireStation testFireStation = new FireStation((List.of("addressList")), 2);
        final List<FireStation> fireStationsList = List.of(testFireStation);
        //when(mockFireStationDao.findAll()).thenReturn(fireStationsList);
        when(mockFireStationDao.savedFireStation(any(FireStation.class))).thenReturn(testFireStation);


       String jsonObject = new Gson().toJson(testFireStation);
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("addressList", List.of("Street").toString());
//        jsonObject.addProperty("station", 2);
        final MockHttpServletResponse response = mockMvc.perform(post("/firestation")
                        .content(jsonObject).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        System.out.println(jsonObject);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void testAddFireStation_NoContent() throws Exception {
        FireStation testFireStation = new FireStation((List.of("addressList")), 2);
        when(mockFireStationDao.savedFireStation(any(FireStation.class))).thenReturn(null);


        String jsonObject = new Gson().toJson(testFireStation);
        final MockHttpServletResponse response = mockMvc.perform(post("/firestation")
                        .content(jsonObject).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        System.out.println(jsonObject);
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
        System.out.println(jsonObject);

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
        System.out.println(jsonObject);

        // Verify the results
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("null", response.getContentAsString());
    }

    @Test
    void testDeleteFireStation() throws Exception {
        // Setup*
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
        // Setup*
        FireStation testFireStation = new FireStation((List.of("addressList")), 2);
        when(mockFireStationDao.deletedFireStation(any(FireStation.class))).thenReturn(null);
        //when(mockFireStationDao.deletedFireStation(new FireStation(List.of("value"), 0))).thenReturn(new FireStation(List.of("value"), 0));

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

        // Configure PersonDao.getPersonFromSameStation(...).
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

        // Configure FireStationDaoImpl.findAll(...).
        final List<FireStation> fireStations = List.of(new FireStation(List.of("value"), 0));
        when(mockFireStationDao.findAll()).thenReturn(fireStations);

        // Configure PersonDao.getPersonFromSameStation(...).
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

//    @Test
//    void testPhoneAlert_FireStationDaoImplReturnsNoItems() throws Exception {
//        // Setup
//        when(mockFireStationDao.findAll()).thenReturn(Collections.emptyList());
//
//        // Configure PersonDao.getPersonFromSameStation(...).
//        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
//        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(personList);
//
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(get("/phoneAlert")
//                .param("firestation", "0")
//                .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertEquals("expectedResponse", response.getContentAsString());
//    }

    @Test
    void testPhoneAlert_PersonDaoReturnsNoItems() throws Exception {
        // Setup

        // Configure FireStationDaoImpl.findAll(...).
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

//    @Test
//    void testPhoneAlert_ThrowsFileNotFoundException() throws Exception {
//        // Setup
//
//        // Configure FireStationDaoImpl.findAll(...).
//        final List<FireStation> fireStations = List.of(new FireStation(List.of("value"), 0));
//        when(mockFireStationDao.findAll()).thenReturn(fireStations);
//
//        // Configure PersonDao.getPersonFromSameStation(...).
//        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
//        when(mockPersonDao.getPersonFromSameStation(0)).thenReturn(personList);
//
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(get("/phoneAlert")
//                .param("firestation", "0")
//                .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
//        assertEquals("expectedResponse", response.getContentAsString());
//    }

    @Test
    void testFlood() throws Exception {
        // Setup

        // Configure PersonDao.getPersonFromSameStation(...).
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

        // Configure PersonDao.findAll(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Configure FireStationDaoImpl.findAll(...).
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

        // Configure PersonDao.findAll(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Configure FireStationDaoImpl.findAll(...).
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
//
//    @Test
//    void testFire_PersonDaoThrowsFileNotFoundException() throws Exception {
//        // Setup
//        when(mockPersonDao.findAll()).thenThrow(FileNotFoundException.class);
//
//        // Configure FireStationDaoImpl.findAll(...).
//        final List<FireStation> fireStations = List.of(new FireStation(List.of("value"), 0));
//        when(mockFireStationDao.findAll()).thenReturn(fireStations);
//
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(get("/fire")
//                .param("queryStringParameters", "queryStringParameters")
//                .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
//        assertEquals("expectedResponse", response.getContentAsString());
//    }

//    @Test
//    void testFire_FireStationDaoImplReturnsNoItems() throws Exception {
//        // Setup
//
//        // Configure PersonDao.findAll(...).
//        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
//        when(mockPersonDao.findAll()).thenReturn(personList);
//
//        when(mockFireStationDao.findAll()).thenReturn(Collections.emptyList());
//
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(get("/fire")
//                .param("queryStringParameters", "queryStringParameters")
//                .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertEquals("expectedResponse", response.getContentAsString());
//    }
}
