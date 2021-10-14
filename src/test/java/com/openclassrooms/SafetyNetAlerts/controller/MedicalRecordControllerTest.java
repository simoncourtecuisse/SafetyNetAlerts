package com.openclassrooms.SafetyNetAlerts.controller;

import com.google.gson.Gson;
import com.openclassrooms.SafetyNetAlerts.JacksonConfiguration;
import com.openclassrooms.SafetyNetAlerts.dao.MedicalRecordDao;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
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
    void testAddMedicalRecord() throws Exception {
        // Setup
        Person person = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        List<Person> personList = List.of(person);
        when(mockPersonDao.findAll()).thenReturn(personList);

        MedicalRecord testMedicalRecord = new MedicalRecord();
        testMedicalRecord.setMedications(List.of("aznol:350mg"));
        testMedicalRecord.setAllergies(List.of("nillacilan"));

        Person secondPerson = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        secondPerson.setMedicalRecord(testMedicalRecord);

        // Run the test
        String jsonObject = new Gson().toJson(secondPerson);
//        System.out.println(jsonObject);
        final MockHttpServletResponse response = mockMvc.perform(post("/medicalRecord")
                .content(jsonObject).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
//        System.out.println(jsonObject);

        // Verify the results
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }


    @Test
    void testUpdateMedicalRecord() throws Exception {
        // Setup
        Person person = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        List<Person> personList = List.of(person);
        when(mockPersonDao.findAll()).thenReturn(personList);

        MedicalRecord testMedicalRecord = new MedicalRecord();
        testMedicalRecord.setMedications(List.of("aznol:350mg"));
        testMedicalRecord.setAllergies(List.of("nillacilan"));
        person.setMedicalRecord(testMedicalRecord);

        Person secondPerson = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        secondPerson.getMedicalRecord().addMedications("aznol:500mg");
        secondPerson.getMedicalRecord().addAllergies("illisoxian");

        // Run the test
        String jsonObject = new Gson().toJson(secondPerson);
//        System.out.println(jsonObject);
        final MockHttpServletResponse response = mockMvc.perform(put("/medicalRecord")
                .content(jsonObject).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertEquals(true, response.getContentAsString().contains("illisoxian"));
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
        final MedicalRecord testMedicalRecord = new MedicalRecord();
        testMedicalRecord.setMedications(List.of("value"));
        testMedicalRecord.setAllergies(List.of("value"));
        when(mockMedicalRecordDao.deletedMedicalRecord(any(MedicalRecord.class))).thenReturn(testMedicalRecord);

        // Run the test
        String jsonObject = new Gson().toJson(testMedicalRecord);
        final MockHttpServletResponse response = mockMvc.perform(delete("/medicalRecord")
                .content(jsonObject).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"medications\":[\"value\"],\"allergies\":[\"value\"]}", response.getContentAsString());
    }

    @Test
    void testUpdateMedicalRecord1() throws Exception {
       final MedicalRecord testMedicalRecord = new MedicalRecord();
       testMedicalRecord.setMedications(List.of("value"));
       testMedicalRecord.setAllergies(List.of("value"));
       when(mockMedicalRecordDao.savedMedicalRecord(any(MedicalRecord.class))).thenReturn(testMedicalRecord);

        // Run the test
        String jsonObject = new Gson().toJson(testMedicalRecord);
//        System.out.println(jsonObject);
        final MockHttpServletResponse response = mockMvc.perform(put("/medicalRecord")
                        .content(jsonObject).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }
}
