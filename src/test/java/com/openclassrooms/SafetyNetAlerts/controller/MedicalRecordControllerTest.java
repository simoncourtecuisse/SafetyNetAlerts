package com.openclassrooms.SafetyNetAlerts.controller;

import com.google.gson.Gson;
import com.openclassrooms.SafetyNetAlerts.JacksonConfiguration;
import com.openclassrooms.SafetyNetAlerts.dao.MedicalRecordDao;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
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

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {MedicalRecordController.class})
//@ExtendWith(SpringExtension.class)
@WebMvcTest(MedicalRecordController.class)
@Import(JacksonConfiguration.class)
class MedicalRecordControllerTest {

    @Autowired
    private MedicalRecordController medicalRecordController;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordDao mockMedicalRecordDao;
    @MockBean
    private PersonDao mockPersonDao;

    @Test
    void testAllMedicalRecords() throws Exception {
        // Setup

        // Configure PersonDao.findAll(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/medicalRecord")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[{\"medications\":[],\"allergies\":[]}]", response.getContentAsString());
    }

    @Test
    void testAllMedicalRecords_PersonDaoReturnsNoItems() throws Exception {
        // Setup
        when(mockPersonDao.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/medicalRecord")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }

//    @Test
//    void testAllMedicalRecords_PersonDaoThrowsFileNotFoundException() throws Exception {
//        // Setup
//        when(mockPersonDao.findAll()).thenThrow(FileNotFoundException.class);
//
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(get("/medicalRecord")
//                .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
//        assertEquals("expectedResponse", response.getContentAsString());
//    }

    @Test
    void testAddMedicalRecord() throws Exception {
        // Setup

        // Configure PersonDao.findAll(...).
//        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
//        when(mockPersonDao.findAll()).thenReturn(personList);
        MedicalRecord testMedicalRecord = new Person("firstName", "lastName", "address", "city", 0, "phone", "email").getMedicalRecord();
      //  final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email").getMedicalRecord());
        when(mockMedicalRecordDao.savedMedicalRecord(any(MedicalRecord.class))).thenReturn(testMedicalRecord);

        // Run the test
        String jsonObject = new Gson().toJson(testMedicalRecord);
        System.out.println(jsonObject);
        final MockHttpServletResponse response = mockMvc.perform(post("/medicalRecord")
                .content(jsonObject).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        System.out.println(jsonObject);

        // Verify the results
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
       // assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testAddMedicalRecord_PersonDaoReturnsNoItems() throws Exception {
        // Setup
        when(mockPersonDao.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/medicalRecord")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testAddMedicalRecord_PersonDaoThrowsFileNotFoundException() throws Exception {
        // Setup
        when(mockPersonDao.findAll()).thenThrow(FileNotFoundException.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/medicalRecord")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testUpdateMedicalRecord() throws Exception {
        // Setup

        // Configure PersonDao.findAll(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/medicalRecord")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testUpdateMedicalRecord_PersonDaoReturnsNoItems() throws Exception {
        // Setup
        when(mockPersonDao.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/medicalRecord")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testUpdateMedicalRecord_PersonDaoThrowsFileNotFoundException() throws Exception {
        // Setup
        when(mockPersonDao.findAll()).thenThrow(FileNotFoundException.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/medicalRecord")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testDeleteMedicalRecord() throws Exception {
        // Setup

        // Configure MedicalRecordDao.deletedMedicalRecord(...).
        final MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setMedications(List.of("value"));
        medicalRecord.setAllergies(List.of("value"));
        when(mockMedicalRecordDao.deletedMedicalRecord(new MedicalRecord())).thenReturn(medicalRecord);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/medicalRecord")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }
}
