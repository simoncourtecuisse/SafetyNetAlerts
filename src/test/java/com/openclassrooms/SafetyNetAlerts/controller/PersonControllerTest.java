package com.openclassrooms.SafetyNetAlerts.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.openclassrooms.SafetyNetAlerts.JacksonConfiguration;
import com.openclassrooms.SafetyNetAlerts.dao.MedicalRecordDao;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDaoImpl;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@AutoConfigureMockMvc
@ContextConfiguration(classes = {PersonController.class})
@WebMvcTest(PersonController.class)
@Import(JacksonConfiguration.class)
class PersonControllerTest {

    @Autowired
    private PersonController personController;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonDaoImpl mockPersonDao;
    @MockBean
    private MedicalRecordDao mockMedicalRecordDao;

    @Test
    void testAllPersons() throws Exception {
        // Setup
        List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        MockHttpServletResponse response = mockMvc.perform(get("/person")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[{\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"location\"" +
                ":{\"address\":\"address\",\"city\":\"city\",\"zip\":0},\"phone\":\"phone\",\"email\"" +
                ":\"email\",\"medicalRecord\":{\"medications\":[],\"allergies\":[]},\"birthdate\":null," +
                "\"age\":0}]", response.getContentAsString());
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
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    void testAllPersons_ThrowsFileNotFoundException() throws Exception {
        // Setup
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
        Person testPerson = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        final List<Person> personList = List.of(testPerson);
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/personInfo")
                        .param("firstName", "firstName")
                        .param("lastName", "lastName")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
//        System.out.println(HttpStatus.OK.value());

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JsonArray jsonArray = new JsonParser().parse(response.getContentAsString()).getAsJsonArray();
        assertEquals(1, jsonArray.size());
    }

    @Test
    void testGetPerson_ReturnsNotFound() throws Exception {
        // Setup
        Person testPerson = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        final List<Person> personList = List.of(testPerson);
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/personInfo")
                        .param("firstName", "serge")
                        .param("lastName", "lastName")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("not found", response.getContentAsString());

    }

    @Test
    void testGetPerson_ReturnsBadRequest() throws Exception {
        // Setup
        Person testPerson = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        final List<Person> personList = List.of(testPerson);
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/personInfo")
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("bad")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
//        System.out.println(response.getStatus());

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("bad request", response.getContentAsString());
    }

    @Test
    void testGetPerson_ThrowsFileNotFoundException() throws Exception {
        // Setup
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
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/communityEmail")
                        .param("city", "city")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[\"FirstName = firstName, LastName = lastName, Email = email\"]", response.getContentAsString());
    }

    @Test
    void testEmailPerson_PersonDaoImplReturnsNoItems() throws Exception {
        // Setup
        when(mockPersonDao.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/communityEmail")
                        .param("city", "city")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    void testEmailPerson_ThrowsFileNotFoundException() throws Exception {
        // Setup
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/communityEmail")
                        .param("city", "city")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    void testAddPerson() throws Exception {
        // Setup
        Person testPerson = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        final List<Person> personList = List.of(testPerson);
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lastName", "serge");
        jsonObject.addProperty("firstName", "pierre");
        jsonObject.addProperty("email", "pierre@gmail.com");
        final MockHttpServletResponse response = mockMvc.perform(post("/person")
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // Verify the results
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void testUpdatePerson() throws Exception {
        // Setup
        Person testPerson = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        final List<Person> personList = List.of(testPerson);
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lastName", "lastName");
        jsonObject.addProperty("firstName", "firstName");
        jsonObject.addProperty("email", "firstName@gmail.com");
        final MockHttpServletResponse response = mockMvc.perform(put("/person")
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        JsonObject jsonObjectResponse = new JsonParser().parse(response.getContentAsString()).getAsJsonObject();
//        System.out.println(response.getContentAsString());

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("firstName@gmail.com", jsonObjectResponse.get("email").getAsString());
    }

    @Test
    void testUpdatePerson_ReturnBadRequest() throws Exception {
        // Setup
        Person testPerson = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        final List<Person> personList = List.of(testPerson);
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
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
        Person testPerson = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        final List<Person> personList = List.of(testPerson);
        when(mockPersonDao.deletedPerson1(any(Person.class))).thenReturn(true);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lastName", "lastName");
        jsonObject.addProperty("firstName", "firstName");
        jsonObject.addProperty("city", "anglet");
        final MockHttpServletResponse response = mockMvc.perform(delete("/person")
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
//        System.out.println(jsonObject);
//        System.out.println(response.getContentAsString());
        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("successful operation", response.getContentAsString());
    }


    @Test
    void testDeletePerson_ReturnBadRequest() throws Exception {
        // Setup
        Person testPerson = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        final List<Person> personList = List.of(testPerson);
        when(mockPersonDao.deletedPerson1(any(Person.class))).thenReturn(true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

    }

    @Test
    void testDeletePerson_ReturnNotFound() throws Exception {
        Person testPerson = new Person("firstName", "lastName", "address", "city", 0, "phone", "email");
        final List<Person> personList = List.of(testPerson);
        when(mockPersonDao.deletedPerson1(any(Person.class))).thenReturn(false);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lastName", "lastName");
        jsonObject.addProperty("firstName", "firstName");
        jsonObject.addProperty("city", "anglet");
        final MockHttpServletResponse response = mockMvc.perform(delete("/person")
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
//        System.out.println(response.getContentAsString());

        // Verify the results
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("not found", response.getContentAsString());
    }

    @Test
    public void testHealth() {
        when(this.mockPersonDao.findAll()).thenReturn(new ArrayList<Person>());
        this.personController.health();
        verify(this.mockPersonDao).findAll();
    }

    @Test
    public void testHealth2() {
        ArrayList<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Simon", "Sou", "42 Main St", "New York", 1, "4105551212", "simonsou@gmail.com"));
        when(this.mockPersonDao.findAll()).thenReturn(personList);
        this.personController.health();
        verify(this.mockPersonDao).findAll();
    }

    @Test
    void testChildAlert() throws Exception {
        // Setup
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/childAlert")
                        .param("address", "address")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[\"FirstName = firstName, LastName = lastName, Age = 0, Family Members = \"]", response.getContentAsString());
    }

    @Test
    void testChildAlert_PersonDaoImplReturnsNoItems() throws Exception {
        // Setup
        when(mockPersonDao.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/childAlert")
                        .param("address", "address")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }
}
