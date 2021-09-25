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
      //  (person.size() == 0)
        return new ResponseEntity<>(person, HttpStatus.OK);
    }


    @GetMapping(value = "/personInfo")
    public ResponseEntity<?> getPerson(@RequestParam String firstName, @RequestParam String lastName) throws FileNotFoundException {
        boolean badRequest = false;
        if ((lastName == null && firstName == null)) badRequest = true ;
        else if (lastName == null || firstName == null) badRequest = true;
        if (badRequest == true) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            List<Person> persons = personDao.findAll();
            List<Person> result = new ArrayList<>();
                result = persons.stream()
                        .filter(p -> (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)))
                        .collect(Collectors.toList());
                if (result.size() == 0){
                    return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
                } else {
                    return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
    }

    @GetMapping(value = "/communityEmail")
    public ResponseEntity<?> emailPerson(@RequestParam String city) throws FileNotFoundException {
        System.out.println(city);
        if (city.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        } else {
            List<Person> persons = personDao.findAll();
            System.out.println(persons);

            return new ResponseEntity<>(persons.stream()
                    .filter(p -> (p.getLocation().getCity().equals(city)))
                    .map(p -> "FirstName = " + p.getFirstName() + ", LastName = " + p.getLastName() + ", Email = " + p.getEmail())
                    .collect(Collectors.toList()), HttpStatus.OK);
        }
    }


    @PostMapping(value = "/person")
    public ResponseEntity<?> addPerson(@RequestBody Person person) {
        System.out.println(person.getFirstName());
        personDao.savedPerson(person);
        return new  ResponseEntity<>("Successful Operation", HttpStatus.CREATED);
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

        if (person == null) {
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
            if (matchingPerson == null) return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
            matchingPerson = person;
            return new ResponseEntity<>(matchingPerson, HttpStatus.OK);
            }
    }


        @DeleteMapping(value = "/person")
        public ResponseEntity<?> deletePerson (@RequestBody Person person){
            System.out.println(person);
            System.out.println(person.getFirstName());
            System.out.println(person.getLastName());
            System.out.println(personDao.findAll());

            if (person == null) {
                return new ResponseEntity<>("null", HttpStatus.BAD_REQUEST);
            } else {
                var deletedPerson = personDao.deletedPerson1(person);
                System.out.println("coucou:" + deletedPerson);

               // boolean personDeleted = personDao.deletedPerson(person);
                if (deletedPerson) return new ResponseEntity<>("successfull operation", HttpStatus.OK);
                else return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
            }
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

