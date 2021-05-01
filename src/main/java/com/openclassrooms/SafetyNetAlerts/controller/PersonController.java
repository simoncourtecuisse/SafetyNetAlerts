package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.dao.MedicalRecordDao;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
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
public class PersonController {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private MedicalRecordDao medicalRecordDao;

    @GetMapping(value = "/person")
    public List<Person> allPersons() throws FileNotFoundException {
        return personDao.findAll();
    }

    @GetMapping(value = "/personInfo")
    public List<Person> getPerson(@RequestParam Map<String, String> queryStringParameters) throws FileNotFoundException {
        String firstName = queryStringParameters.get("firstName");
        String lastName = queryStringParameters.get("lastName");

        List<Person> persons = personDao.findAll();
        if (firstName != null && lastName != null) {
            return persons.stream()
                    .filter(p -> (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)))
                    .collect(Collectors.toList());
        } else {
            return persons.stream()
                    .filter(p -> (p.getLastName().equals(lastName)))
                    .collect(Collectors.toList());
        }
    }


    @GetMapping(value = "/personInfo/{id}")
    public List<Person> getPerson(@PathVariable(value = "id") String id, @RequestParam Map<String, String> queryStringParameters) throws FileNotFoundException {
        String[] names = id.split("-");
        return personDao.findAll();
    }

    @PostMapping(value = "/testpost")
    public String safetyNet2() {
        personDao.addPerson("Simon", "Courtecuisse");
        return "safetyNet is working 2";
    }

    @PostConstruct
	public void initdata() throws IOException, ParseException {
		List<Person> persons = personDao.initPersons();
        medicalRecordDao.initMedicalRecords(persons);
	}
}
