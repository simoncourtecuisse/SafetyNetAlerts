package com.openclassrooms.SafetyNetAlerts.model;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FireStationCount {
    public MappingJacksonValue personList;
    public long countAdults;
    public long countChilds;

}

//    @GetMapping(value = "/fire")
//    public List<String> fire(@RequestParam Map<String, String> queryStringParameters) throws FileNotFoundException {
//        String address = queryStringParameters.get("address");
//
//        List<Person> persons = personDao.findAll();
//        List<FireStation> stations = fireStationDao.findAll();
//
////        var stationStream = stations.stream().filter(s -> s.getAddressList().contains(address))
////                .collect(Collectors.toList());
//        List<String> listStations = stations.stream()
//                .filter(s -> s.getAddressList().contains(address))
//                .map(s -> "Station Number = " + s.getStation())
//                .collect(Collectors.toList());
//        return listStations;
////        var personStream = persons.stream()
////                .filter(p -> p.getLocation().getAddress().equals(address))
////                .map(p -> "FisrtName = " + p.getFirstName() +
////                        ", LastName = " + p.getLastName() +
////                        ", Phone = " + p.getPhone() +
////                        ", Age = " + p.getAge() +
////                        ", MedicalRecord = Medications: " + p.getMedicalRecord().getMedications() + ", Allergies: " + p.getMedicalRecord().getAllergies() )
////                .collect(Collectors.toList());
//        List<String> listPersons = persons.stream()
//                .filter(p -> p.getLocation().getAddress().equals(address))
//                .map(p -> "FisrtName = " + p.getFirstName() +
//                        ", LastName = " + p.getLastName() +
//                        ", Phone = " + p.getPhone() +
//                        ", Age = " + p.getAge() +
//                        ", MedicalRecord = Medications: " + p.getMedicalRecord().getMedications() + ", Allergies: " + p.getMedicalRecord().getAllergies() )
//                .collect(Collectors.toList());
//
//        System.out.println(listStations);
//
//        if (address != null) {
//            return new List<String> fireList = Stream.concat(persons.stream(), stations.stream());
//        } else return new ArrayList<>();
//
//    }