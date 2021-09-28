package com.openclassrooms.SafetyNetAlerts.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.google.gson.JsonArray;
import com.openclassrooms.SafetyNetAlerts.dao.FireStationDaoImpl;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;

import java.io.IOException;
import java.text.ParseException;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {FireStationController.class})
@ExtendWith(SpringExtension.class)
public class FireStationControllerTest {
    @Autowired
    private FireStationController fireStationController;

    @MockBean
    private FireStationDaoImpl fireStationDaoImpl;

    @MockBean
    private PersonDao personDao;

    @Test
    public void testInitdata() throws IOException, ParseException {
        // TODO: This test is incomplete.
        //   Reason: Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     FireStationController.fireStationDao
        //     FireStationController.personDao

        when(this.fireStationDaoImpl.initFireStations()).thenReturn(new ArrayList<FireStation>());
        this.fireStationController.initdata();
    }

    @Test
    public void testFlood() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/flood");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.fireStationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

   @Test
    public void testPhoneAlert() throws Exception {
        when(this.personDao.getPersonFromSameStation((Integer) any())).thenReturn(new ArrayList<Person>());
        when(this.fireStationDaoImpl.findAll()).thenReturn(new ArrayList<FireStation>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/phoneAlert").param("firestation", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.fireStationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("null"));
    }


}

