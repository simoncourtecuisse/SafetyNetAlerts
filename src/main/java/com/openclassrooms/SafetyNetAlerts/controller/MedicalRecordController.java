package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.dao.MedicalRecordDao;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return personDao.findAll().stream().map(person -> person.getMedicalRecord()).collect(Collectors.toList());
    }

    @PostMapping(value = "/medicalRecord")
    public ResponseEntity<Void> addMedicalRecord(@RequestBody Person person) throws FileNotFoundException {
        List<Person> persons = personDao.findAll();
        Person matchingMedicalRecord = persons.stream()
                .filter(p -> (p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName())))
                .findAny().orElse(null);
        matchingMedicalRecord.setMedicalRecord(person.getMedicalRecord());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{medications}")
                .buildAndExpand(matchingMedicalRecord.getMedicalRecord())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/medicalRecord")
    public ResponseEntity<?> updateMedicalRecord(@RequestBody Person person) throws FileNotFoundException {
        List<Person> persons = personDao.findAll();
        if (persons == null) {
            return new ResponseEntity<String>("null", HttpStatus.BAD_REQUEST);
        } else {
            Person matchingMedicalRecord = persons.stream()
                    .filter(p -> (p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName())))
                    .findAny().orElse(null);
            matchingMedicalRecord.setMedicalRecord((person.getMedicalRecord()));
            return new ResponseEntity<>(matchingMedicalRecord, HttpStatus.OK);
        }
    }


    @DeleteMapping(value = "/medicalRecord")
    public ResponseEntity<?> deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        MedicalRecord medicalRecordDeleted = medicalRecordDao.deletedMedicalRecord(medicalRecord);
        if (medicalRecordDeleted == null) {
            return new ResponseEntity<String>("null", HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>(medicalRecordDeleted, HttpStatus.OK);
    }

    @PostConstruct
    public void initdata() throws IOException, ParseException {
        List<Person> persons = personDao.initPersons();
        medicalRecordDao.initMedicalRecords(persons);
    }

}
