package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.dao.MedicalRecordDao;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDaoImpl;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonDaoImpl mockPersonDao;
    @MockBean
    private MedicalRecordDao mockMedicalRecordDao;

    @Test
    void testAllPersons() throws Exception {
        // Setup

        // Configure PersonDaoImpl.findAll(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/person")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testAllPersons_PersonDaoImplReturnsNoItems() throws Exception {
        // Setup
        when(mockPersonDao.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/person")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testAllPersons_ThrowsFileNotFoundException() throws Exception {
        // Setup

        // Configure PersonDaoImpl.findAll(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/person")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testGetPerson() throws Exception {
        // Setup

        // Configure PersonDaoImpl.findAll(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/personInfo")
                .param("firstName", "firstName")
                .param("lastName", "lastName")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testGetPerson_PersonDaoImplReturnsNoItems() throws Exception {
        // Setup
        when(mockPersonDao.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/personInfo")
                .param("firstName", "firstName")
                .param("lastName", "lastName")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testGetPerson_ThrowsFileNotFoundException() throws Exception {
        // Setup

        // Configure PersonDaoImpl.findAll(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/personInfo")
                .param("firstName", "firstName")
                .param("lastName", "lastName")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testEmailPerson() throws Exception {
        // Setup

        // Configure PersonDaoImpl.findAll(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/communityEmail")
                .param("queryStringParameters", "queryStringParameters")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testEmailPerson_PersonDaoImplReturnsNoItems() throws Exception {
        // Setup
        when(mockPersonDao.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/communityEmail")
                .param("queryStringParameters", "queryStringParameters")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testEmailPerson_ThrowsFileNotFoundException() throws Exception {
        // Setup

        // Configure PersonDaoImpl.findAll(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/communityEmail")
                .param("queryStringParameters", "queryStringParameters")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testAddPerson() throws Exception {
        // Setup

        // Configure PersonDaoImpl.savedPerson(...).
        final Person person = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        when(mockPersonDao.savedPerson(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"))).thenReturn(person);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/person")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testUpdatePerson() throws Exception {
        // Setup

        // Configure PersonDaoImpl.findAll(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/person")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testUpdatePerson_PersonDaoImplReturnsNoItems() throws Exception {
        // Setup
        when(mockPersonDao.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/person")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testUpdatePerson_ThrowsFileNotFoundException() throws Exception {
        // Setup

        // Configure PersonDaoImpl.findAll(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/person")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testDeletePerson() throws Exception {
        // Setup

        // Configure PersonDaoImpl.deletedPerson(...).
        final Person person = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        when(mockPersonDao.deletedPerson(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"))).thenReturn(person);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/person")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testChildAlert() throws Exception {
        // Setup

        // Configure PersonDaoImpl.findAll(...).
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/childAlert")
                .param("queryStringParameters", "queryStringParameters")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testChildAlert_PersonDaoImplReturnsNoItems() throws Exception {
        // Setup
        when(mockPersonDao.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/childAlert")
                .param("queryStringParameters", "queryStringParameters")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }
}
