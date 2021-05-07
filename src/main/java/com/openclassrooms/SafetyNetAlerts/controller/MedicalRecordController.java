package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.dao.MedicalRecordDao;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
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

    @PostMapping(value = "/medicalRecord")
    public ResponseEntity<Void> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {

        MedicalRecord medicalRecordAdded = medicalRecordDao.savedMedicalRecord(medicalRecord);
        if (medicalRecordAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{medications}")
                .buildAndExpand(medicalRecordAdded.getMedications())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/medicalRecord")
    public void updateMedicalRecord (@RequestBody MedicalRecord medicalRecord) {
        medicalRecordDao.savedMedicalRecord(medicalRecord);
    }

    @DeleteMapping(value = "/medicalRecord")
    public void deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordDao.deletedMedicalRecord(medicalRecord);
    }

    @PostConstruct
    public void initdata() throws IOException, ParseException {
        List<Person> persons = personDao.initPersons();
        medicalRecordDao.initMedicalRecords(persons);
    }

}
