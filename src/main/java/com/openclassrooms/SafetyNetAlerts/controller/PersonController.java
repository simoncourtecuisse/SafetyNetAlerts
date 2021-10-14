package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.dao.MedicalRecordDao;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDaoImpl;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PersonController implements HealthIndicator {

    private static final Logger LOGGER = LogManager
            .getLogger(PersonController.class);

    @Autowired
    private PersonDaoImpl personDao;

    @Autowired
    private MedicalRecordDao medicalRecordDao;

    @Override
    public Health health() {
        List<Person> persons = personDao.findAll();

        if (persons.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }

    @GetMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> allPersons() throws FileNotFoundException {
        List<Person> person = personDao.findAll();
        LOGGER.debug("Get request sent from the PersonController to retrieve all persons from database");
        return new ResponseEntity<>(person, HttpStatus.OK);
    }


    @GetMapping(value = "/personInfo")
    public ResponseEntity<?> getPerson(@RequestParam String firstName, @RequestParam String lastName) throws FileNotFoundException {
        LOGGER.debug("Get request sent from the PersonController to retrieve information belonging to" + " '" + firstName + " " + lastName + "'");
        boolean badRequest = false;
        if ((lastName == null && firstName == null)) badRequest = true;
        else if (lastName == null || firstName == null) badRequest = true;
        if (badRequest) {
            LOGGER.error("Failed to retrieve person because of a BAD REQUEST");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            List<Person> persons = personDao.findAll();
            List<Person> result = new ArrayList<>();
            result = persons.stream()
                    .filter(p -> (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)))
                    .collect(Collectors.toList());
            if (result.size() == 0) {
                LOGGER.error("Failed to retrieve person because the person was not found");
                return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
            } else {
                LOGGER.info("'" + firstName + " " + lastName + "' retrieved successfully");
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
    }

    @GetMapping(value = "/communityEmail")
    public ResponseEntity<?> emailPerson(@RequestParam String city) throws FileNotFoundException {
//        System.out.println(city);
        LOGGER.debug("Get request sent from the PersonController");
        if (city.isEmpty()) {
            LOGGER.error("Failed to get community email because of a BAD REQUEST");
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        } else {
            LOGGER.info("Community email retrieved successfully");
            List<Person> persons = personDao.findAll();
//            System.out.println(persons);
            return new ResponseEntity<>(persons.stream()
                    .filter(p -> (p.getLocation().getCity().equals(city)))
                    .map(p -> "FirstName = " + p.getFirstName() + ", LastName = " + p.getLastName() + ", Email = " + p.getEmail())
                    .collect(Collectors.toList()), HttpStatus.OK);
        }
    }


    @PostMapping(value = "/person")
    public ResponseEntity<?> addPerson(@RequestBody Person person) {
//        System.out.println(person.getFirstName());
        personDao.savedPerson(person);
        LOGGER.debug("Post request sent from the PersonController");
        return new ResponseEntity<>("Successful Operation", HttpStatus.CREATED);
    }

    @PutMapping(value = "/person")
    public ResponseEntity<?> updatePerson(@RequestBody Person person) throws FileNotFoundException {

        LOGGER.debug("Put request sent from the PersonController");
        if (person == null) {
            LOGGER.error("Failed to update person because of a BAD REQUEST");
            return new ResponseEntity<>("null", HttpStatus.BAD_REQUEST);
        } else {
            List<Person> persons = personDao.findAll();
            Person matchingPerson = persons.stream()
                    .filter(p -> (p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName())))
                    .findAny().orElse(null);
            matchingPerson.setLocation(person.getLocation());
            matchingPerson.setMedicalRecord(person.getMedicalRecord());
            matchingPerson.setEmail(person.getEmail());
            matchingPerson.setPhone(person.getPhone());
            matchingPerson.setBirthdate(person.getBirthdate());
            if (matchingPerson == null) {
                LOGGER.error("Failed to update person because the person was not found");
                return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
            }
            matchingPerson = person;
            LOGGER.info("'" + person.getFirstName() + " " + person.getLastName() + "' updated successfully");
            return new ResponseEntity<>(matchingPerson, HttpStatus.OK);
        }
    }


    @DeleteMapping(value = "/person")
    public ResponseEntity<?> deletePerson(@RequestBody Person person) {
//            System.out.println(person);
//            System.out.println(person.getFirstName());
//            System.out.println(person.getLastName());
//            System.out.println(personDao.findAll());
        LOGGER.debug("Delete request sent from the PersonController");
        if (person == null) {
            LOGGER.error("Failed to delete person because of a BAD REQUEST");
            return new ResponseEntity<>("null", HttpStatus.BAD_REQUEST);
        } else {
            var deletedPerson = personDao.deletedPerson1(person);

            // boolean personDeleted = personDao.deletedPerson(person);

            if (deletedPerson) {
                LOGGER.info("'" + person.getFirstName() + " " + person.getLastName() + "' deleted successfully");
                return new ResponseEntity<>("successful operation", HttpStatus.OK);
            } else
                LOGGER.error("Failed to delete person because the person was not found");
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/childAlert")
    public ResponseEntity<?> childAlert(@RequestParam String address) {
//            System.out.println(address);
        LOGGER.debug("Get request sent from the PersonController to retrieve children living at " + address);
        List<Person> persons = personDao.findAll();
        if (address.isEmpty()) {
            LOGGER.error("Failed to retrieve children living at " + address);
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        } else {
            LOGGER.info("Children retrieved successfully");
            return new ResponseEntity<>(persons.stream()
                    .filter(p -> p.getLocation().getAddress().equals(address))
                    .filter(p -> p.getAge() <= 18)
                    .map(p -> "FirstName = " + p.getFirstName() + ", LastName = " + p.getLastName() + ", Age = " + p.getAge() +
                            ", Family Members = " + persons.stream()
                            .filter(f -> f.getLocation().getAddress().equals(address))
                            .filter(f -> !f.equals(p))
                            .map(f -> f.getFirstName() + " " + f.getLastName())
                            .collect(Collectors.joining(", ")))
                    .collect(Collectors.toList()),
                    HttpStatus.OK);
        }
    }

}

