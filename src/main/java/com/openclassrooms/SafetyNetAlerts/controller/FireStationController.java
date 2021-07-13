package com.openclassrooms.SafetyNetAlerts.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.gson.JsonObject;
import com.openclassrooms.SafetyNetAlerts.dao.FireStationDao;
import com.openclassrooms.SafetyNetAlerts.dao.FireStationDaoImpl;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.FireStationCount;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.*;
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
    public void updateFireStation(@RequestBody FireStation fireStation) {
        fireStationDao.savedFireStation(fireStation);
    }

    @DeleteMapping(value = "/firestation")
    public void deleteFireStation(@RequestBody FireStation fireStation) {
        fireStationDao.deletedFireStation(fireStation);

    }

    @GetMapping(value = "/firestation", produces= MediaType.APPLICATION_JSON_VALUE)
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
// @GetMapping(value = "/firestation")
//    public ResponseEntity<?> getPersonByFireStation(@RequestParam Map<String, String> queryStringParameters) {
//        String stationNumber = queryStringParameters.get("station");
//
//        List<Person> result = personDao
//                .getPersonFromSameStation(Integer.parseInt(stationNumber));
//        long countAdults = result
//                .stream()
//                .filter(p -> p.getAge() >= 18)
//                .count();
//        long countChilds = result
//                .stream()
//                .filter(p -> p.getAge() < 18)
//                .count();
//        FireStationCount fireStationCount = new FireStationCount();
//        fireStationCount.countAdults = countAdults;
//        fireStationCount.countChilds = countChilds;
//        fireStationCount.personList = result;
//
//
//        if (result == null) {
//            return new ResponseEntity<String>("null", HttpStatus.NOT_FOUND);
//        } else return new ResponseEntity<FireStationCount>(fireStationCount, HttpStatus.OK);
//
//    }

    @GetMapping(value = "/phoneAlert")
    public List<String> phoneAlert(@RequestParam String firestation) throws FileNotFoundException {


        List<FireStation> stations = fireStationDao.findAll();
        List<Person> persons = personDao.getPersonFromSameStation(Integer.parseInt(firestation));
        System.out.println(persons);


        var stationsPhone = stations.stream()
                .filter(s -> s.getStation().equals(Integer.parseInt(firestation)))
                .collect(Collectors.toList());

        var personPhone = persons.stream()
                .map(p -> "FirstName = " + p.getFirstName() + ", LastName = " + p.getLastName() + ", Phone = " + p.getPhone())
                .collect(Collectors.toList());
        System.out.println(stationsPhone);
        System.out.println(personPhone);


        return personPhone;
    }

    @GetMapping(value = "/flood")
    public ResponseEntity<?> flood(@RequestParam List<Integer> stations) throws FileNotFoundException, JsonProcessingException {
        System.out.println(stations);

        List<Person> result = new ArrayList<>();

        for(int stationNumber : stations) {
            System.out.println(stationNumber);
            List<Person> persons = personDao.getPersonFromSameStation(stationNumber);

//            var personFlood = persons.stream()
//                    .map(p -> "FirstName = " + p.getFirstName() +
//                        ", LastName = " + p.getLastName() +
//                        ", Phone = " + p.getPhone() +
//                        ", Age = " + p.getAge() +
//                        ", Address = " + p.getLocation().getAddress() +
//                        ", MedicalRecord = Medications: " + p.getMedicalRecord().getMedications() +
//                        ", Allergies: " + p.getMedicalRecord().getAllergies())
//                    .collect(Collectors.toList());
//
//            result.addAll(personFlood);
            String[] exceptions = {"email"};

            JsonObject stringObjectMap = new JsonObject();
            stringObjectMap.add("person", fireStationDao.filterResult(exceptions, persons));


            if (result == null) {
                return new ResponseEntity<String>("null", HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(stringObjectMap.toString(), HttpStatus.OK);

        }
        return null;
    }



//
//        List<FireStation> stations = fireStationDao.findAll();
//        List<Person> persons = personDao.getPersonFromSameStation(Integer.parseInt(stationNumber));
//        System.out.println(stations);
//        System.out.println(persons);
//
//        var stationsFlood = stations.stream()
//                .filter(s -> s.getStation().equals(Integer.parseInt(stationNumber)))
//                .collect(Collectors.toList());
//
//        var personsFlood = persons.stream()
//                .filter(p -> p.getLocation().getAddress().equals(stationNumber))
//                .map(p -> "FirstName = " + p.getFirstName() +
//                        ", LastName = " + p.getLastName() +
//                        ", Phone = " + p.getPhone() +
//                        ", Age = " + p.getAge() +
//                        ", MedicalRecord = Medications: " + p.getMedicalRecord().getMedications() +
//                        ", Allergies: " + p.getMedicalRecord().getAllergies())
//                .sorted()
//                .collect(Collectors.toList());
//
//        System.out.println(stationsFlood);
//        System.out.println(personsFlood);
//
//        return personsFlood;




    @GetMapping(value = "/fire")
    public List<String> fire(@RequestParam Map<String, String> queryStringParameters) throws FileNotFoundException {
        String address = queryStringParameters.get("address");

        List<Person> persons = personDao.findAll();
        List<FireStation> stations = fireStationDao.findAll();

        if (address != null) {
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

            return fireList;
        } else return new ArrayList<>();

    }


    @PostConstruct
    public void initdata() throws IOException, ParseException {
        fireStationDao.initFireStations();
    }

}
