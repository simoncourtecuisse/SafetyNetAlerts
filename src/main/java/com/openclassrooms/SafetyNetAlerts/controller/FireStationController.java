package com.openclassrooms.SafetyNetAlerts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.openclassrooms.SafetyNetAlerts.dao.FireStationDaoImpl;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class FireStationController {

    @Autowired
    private FireStationDaoImpl fireStationDao;

    @Autowired
    private PersonDao personDao;


//    @GetMapping(value = "/firestation")
//    public List<FireStation> allFireStations() throws FileNotFoundException {
//        return fireStationDao.findAll();
//    }

    @PostMapping(value = "/firestation")
    public ResponseEntity<Void> addFireStation(@RequestBody FireStation fireStation) {

        FireStation fireStationAdded = fireStationDao.savedFireStation(fireStation);
        if (fireStationAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{address}")
                .buildAndExpand(fireStationAdded.getAddressList())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/firestation")
    public ResponseEntity<?> updateFireStation(@RequestBody FireStation fireStation) {
        FireStation fireStationPut = fireStationDao.savedFireStation(fireStation);
        if (fireStationPut == null) {
            return new ResponseEntity<String>("null", HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>(fireStationPut, HttpStatus.OK);
    }

    @DeleteMapping(value = "/firestation")
    public ResponseEntity<?> deleteFireStation(@RequestBody FireStation fireStation) {
        FireStation fireStationDeleted = fireStationDao.deletedFireStation(fireStation);
        if (fireStationDeleted == null) {
            return new ResponseEntity<String>("null", HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>(fireStationDeleted, HttpStatus.OK);
    }

    @GetMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPersonByFireStation(@RequestParam String station) throws JsonProcessingException {
        List<Person> result = personDao
                .getPersonFromSameStation(Integer.parseInt(station));
        long countAdults = result
                .stream()
                .filter(p -> p.getAge() >= 18)
                .count();
        long countChilds = result
                .stream()
                .filter(p -> p.getAge() < 18)
                .count();

        String[] exceptions = {"medicalRecord", "email"};

        JsonObject stringObjectMap = new JsonObject();
        stringObjectMap.addProperty("adults", countAdults);
        stringObjectMap.addProperty("childs", countChilds);
        stringObjectMap.add("person", fireStationDao.filterResult(exceptions, result));

        if (result == null) {
            return new ResponseEntity<String>("null", HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>(stringObjectMap.toString(), HttpStatus.OK);

    }

    @GetMapping(value = "/phoneAlert")
    public ResponseEntity<?> phoneAlert(@RequestParam String firestation) throws FileNotFoundException {

        List<FireStation> stations = fireStationDao.findAll();
        List<Person> persons = personDao.getPersonFromSameStation(Integer.parseInt(firestation));
        System.out.println(persons);
        boolean badRequest = false;
        if (firestation.isEmpty()) badRequest = true;

        var stationsPhone = stations.stream()
                .filter(s -> s.getStation().equals(Integer.parseInt(firestation)))
                .collect(Collectors.toList());

        var personPhone = persons.stream()
                .map(p -> "FirstName = " + p.getFirstName() + ", LastName = " + p.getLastName() + ", Phone = " + p.getPhone())
                .collect(Collectors.toList());
        System.out.println(stationsPhone);
        System.out.println(personPhone);


        if (badRequest) return new ResponseEntity<>("null", HttpStatus.BAD_REQUEST);
        if (personPhone.isEmpty()) {
            return new ResponseEntity<String>("null", HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>(personPhone, HttpStatus.OK);
    }

    @GetMapping(value = "/flood", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> flood(@RequestParam List<String> stations) throws FileNotFoundException, JsonProcessingException {
        System.out.println(stations);

        boolean badRequest = false;
        if (stations.isEmpty()) badRequest = true;
        List<Person> result = new ArrayList<>();

        for (String stationNumber : stations) {
            try {
                int number = Integer.parseInt(stationNumber);
                System.out.println(stationNumber);
                List<Person> persons = personDao.getPersonFromSameStation(number);
                result.addAll(persons);
            } catch (Exception e) {
                badRequest = true;
            }
        }
        if (badRequest) return new ResponseEntity<>("null", HttpStatus.BAD_REQUEST);
        if (result.isEmpty()) {
            return new ResponseEntity<String>("null", HttpStatus.NOT_FOUND);
        } else {
            String[] exceptions = {"email"};
            JsonObject stringObjectMap = new JsonObject();
            stringObjectMap.add("person", fireStationDao.filterResult(exceptions, result));
            return new ResponseEntity<>(stringObjectMap.toString(), HttpStatus.OK);
        }
    }


    @GetMapping(value = "/fire")
    public ResponseEntity<?> fire(@RequestParam Map<String, String> queryStringParameters) throws FileNotFoundException {
        String address = queryStringParameters.get("address");

        List<Person> persons = personDao.findAll();
        List<FireStation> stations = fireStationDao.findAll();

        if (address.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        } else {
            var stationStream = stations.stream()
                    .filter(s -> s.getAddressList().contains(address))
                    .map(s -> "Station Number = " + s.getStation())
                    .collect(Collectors.toList());

            System.out.println(stationStream);

            var personStream = persons.stream()
                    .filter(p -> p.getLocation().getAddress().equals(address))
                    .map(p -> "FisrtName = " + p.getFirstName() +
                            ", LastName = " + p.getLastName() +
                            ", Phone = " + p.getPhone() +
                            ", Age = " + p.getAge() +
                            ", MedicalRecord = Medications: " + p.getMedicalRecord().getMedications() +
                            ", Allergies: " + p.getMedicalRecord().getAllergies())
                    .collect(Collectors.toList());

            List<String> fireList = new ArrayList<>();
            fireList.addAll(personStream);
            fireList.addAll(stationStream);
            return new ResponseEntity<>(fireList, HttpStatus.OK);
        }

    }

    @PostConstruct
    public void initdata() throws IOException, ParseException {
        fireStationDao.initFireStations();
    }

}
