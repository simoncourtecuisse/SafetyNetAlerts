package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.dao.FireStationDao;
import com.openclassrooms.SafetyNetAlerts.dao.MedicalRecordDao;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class FireStationController {

    @Autowired
    private FireStationDao fireStationDao;


    @GetMapping(value = "/firestation")
    public List<FireStation> allFireStations() throws FileNotFoundException {
        return fireStationDao.findAll();
    }


    @PostConstruct
    public void initdata() throws IOException, ParseException {
        fireStationDao.initFireStations();
    }

}
