package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.dao.MedicalRecordDao;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordDao medicalRecordDao;
    @Autowired
    private PersonDao personDao;

    @GetMapping(value = "/medicalRecord")
    public List<MedicalRecord> allMedicalRecords() throws FileNotFoundException {
        return personDao.findAll().stream().map(person->person.getMedicalRecord()).collect(Collectors.toList());
    }

    @PostConstruct
    public void initdata() throws IOException, ParseException {
        List<Person> persons = personDao.initPersons();
        medicalRecordDao.initMedicalRecords(persons);
    }

}
