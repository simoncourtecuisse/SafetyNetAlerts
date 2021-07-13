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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
class PersonControllerTest1 {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonDaoImpl mockPersonDao;
    @MockBean
    private MedicalRecordDao mockMedicalRecordDao;


    @Test
    void testHealth() {
    }

    @Test
    void testAllPersons() throws Exception {
        final List<Person> personList = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        when(mockPersonDao.findAll()).thenReturn(personList);

        final MockHttpServletResponse response = mockMvc.perform(get("/person")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(personList.toString(), response.getContentAsString());
    }

    @Test
    void testGetPerson() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/person");
        MvcResult result = mockMvc.perform(request).andReturn();
        assertEquals("", result.getResponse().getContentAsString());
    }

    @Test
    void testEmailPerson() {
    }

    @Test
    void testAddPerson() {
    }

    @Test
    void testUpdatePerson() {
    }

    @Test
    void testDeletePerson() {
    }

    @Test
    void testChildAlert() {
    }
}