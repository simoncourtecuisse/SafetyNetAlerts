package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.dao.MedicalRecordDao;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDaoImpl;
import com.openclassrooms.SafetyNetAlerts.model.Location;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class PersonController implements HealthIndicator {

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
        if (person == null) {
            return new ResponseEntity<String>("null", HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>(person, HttpStatus.OK);
    }


    @GetMapping(value = "/personInfo")
    public ResponseEntity<?> getPerson(@RequestParam String firstName, @RequestParam String lastName) throws FileNotFoundException {
        List<Person> persons = personDao.findAll();

        if (firstName != null && lastName != null) {
            return new ResponseEntity<>(persons.stream()
                    .filter(p -> (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)))
                    .collect(Collectors.toList()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(persons.stream()
                    .filter(p -> (p.getLastName().equals(lastName)))
                    .collect(Collectors.toList()), HttpStatus.OK);
        }
    }
//    @GetMapping(value = "/personInfo")
//    public List<Person> getPerson(@RequestParam Map<String, String> queryStringParameters) throws FileNotFoundException {
//        String firstName = queryStringParameters.get("firstName");
//        String lastName = queryStringParameters.get("lastName");
//
//        List<Person> persons = personDao.findAll();
//        if (firstName != null && lastName != null) {
//            return persons.stream()
//                    .filter(p -> (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)))
//                    .collect(Collectors.toList());
//        } else {
//            return persons.stream()
//                    .filter(p -> (p.getLastName().equals(lastName)))
//                    .collect(Collectors.toList());
//        }
//    }

    @GetMapping(value = "/communityEmail")
    public ResponseEntity<?> emailPerson(@RequestParam String city) throws FileNotFoundException {
        System.out.println(city);
        List<Person> persons = personDao.findAll();
        if (city.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        } else {
            System.out.println(persons);

            return new ResponseEntity<>(persons.stream()
                    .filter(p -> (p.getLocation().getCity().equals(city)))
                    .map(p -> "FirstName = " + p.getFirstName() + ", LastName = " + p.getLastName() + ", Email = " + p.getEmail())
                    .collect(Collectors.toList()), HttpStatus.OK);

        }
    }


    @PostMapping(value = "/person")
    public ResponseEntity<Void> addPerson(@RequestBody Person person) {

        Person personAdded = personDao.savedPerson(person);
        if (personAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{firstName}")
                .buildAndExpand(personAdded.getFirstName())
                .toUri();
        return ResponseEntity.created(location).build();
    }

//    @PutMapping(value = "/person/{lastName}/{firstName}")
//    public void updatePerson(@RequestBody Person person, @PathVariable String lastName, @PathVariable String firstName) throws FileNotFoundException {
//        List<Person> persons = personDao.findAll();
//        Person matchingPerson = persons.stream()
//                .filter(p -> (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)))
//                .findAny().orElse(null);
//        matchingPerson.setLocation(person.getLocation());
//    }

    @PutMapping(value = "/person")
    public ResponseEntity<?> updatePerson(@RequestBody Person person) throws FileNotFoundException {
        List<Person> persons = personDao.findAll();
        if (persons == null) {
            return new ResponseEntity<>("null", HttpStatus.NOT_FOUND);
        } else {
            Person matchingPerson = persons.stream()
                    .filter(p -> (p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName())))
                    .findAny().orElse(null);
            if (person.getLocation() != null) {
                matchingPerson.setLocation(person.getLocation());
            }
            if (person.getPhone() != null) {
                matchingPerson.setPhone(person.getPhone());
            }
            if (person.getEmail() != null) {
                matchingPerson.setEmail(person.getEmail());
            }

            if (person.getMedicalRecord()
                    .getMedications().size() != 0) {
                matchingPerson.getMedicalRecord().setMedications(person.getMedicalRecord().getMedications());
            }
            System.out.println(person.getMedicalRecord()
                    .getMedications().size());
            System.out.println(person.getMedicalRecord()
                    .getMedications().size() != 0);

            if (person.getMedicalRecord()
                    .getAllergies().size() != 0) {
                matchingPerson.getMedicalRecord().setAllergies(person.getMedicalRecord().getAllergies());
            }

            if (person.getBirthdate() != null) {
                matchingPerson.setBirthdate(person.getBirthdate());
            }
            return new ResponseEntity<>(matchingPerson, HttpStatus.OK);
        }
    }
//    @PutMapping(value = "/person")
//    public void updatePerson(@RequestBody Person person) throws FileNotFoundException {
//        List<Person> persons = personDao.findAll();
//        Person matchingPerson = persons.stream()
//                .filter(p -> (p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName())))
//                .findAny().orElse(null);
//        if (person.getLocation() != null) {
//            matchingPerson.setLocation(person.getLocation());
//        }
//        if (person.getPhone() != null) {
//            matchingPerson.setPhone(person.getPhone());
//        }
//        if (person.getEmail() != null) {
//            matchingPerson.setEmail(person.getEmail());
//        }
//
//        if (person.getMedicalRecord()
//                .getMedications().size() != 0) {
//            matchingPerson.getMedicalRecord().setMedications(person.getMedicalRecord().getMedications());
//        }
//        System.out.println(person.getMedicalRecord()
//                .getMedications().size());
//        System.out.println(person.getMedicalRecord()
//                .getMedications().size() != 0);
//
//        if (person.getMedicalRecord()
//                .getAllergies().size() != 0) {
//            matchingPerson.getMedicalRecord().setAllergies(person.getMedicalRecord().getAllergies());
//        }
//
//        if (person.getBirthdate() != null) {
//            matchingPerson.setBirthdate(person.getBirthdate());
//        }
//    }


        @DeleteMapping(value = "/person")
        public ResponseEntity<?> deletePerson (@RequestBody Person person){
            Person personDeleted = personDao.deletedPerson(person);
            if (personDeleted == null) {
                return new ResponseEntity<>("null", HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(personDeleted, HttpStatus.OK);
        }


//    @GetMapping(value = "/firestation/{stationNumber}")
//    public ResponseEntity<?> getPersonByFireStation(@PathVariable String stationNumber) {
//
//        List<Person> result = personDao.getPersonFromSameStation(Integer.parseInt(stationNumber));
//        if (result == null) {
//            return new ResponseEntity<String>("null", HttpStatus.NOT_FOUND);
//        } else return new ResponseEntity<List<Person>>(result, HttpStatus.OK);
//
//    }

        @GetMapping(value = "/childAlert")
        public ResponseEntity<?> childAlert (@RequestParam String address){
            System.out.println(address);
            List<Person> persons = personDao.findAll();

            if (address.isEmpty()) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
            } else {
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

