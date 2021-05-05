package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.dao.FireStationDao;
import com.openclassrooms.SafetyNetAlerts.dao.MedicalRecordDao;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
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
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class FireStationController {

    @Autowired
    private FireStationDao fireStationDao;


    @GetMapping(value = "/firestation")
    public List<FireStation> allFireStations() throws FileNotFoundException {
        return fireStationDao.findAll();
    }

    @PostMapping(value = "/firestation")
    public ResponseEntity<Void> addFireStation(@RequestBody FireStation fireStation) {

        FireStation fireStationAdded = fireStationDao.savedFireStation(fireStation);
        if (fireStationAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{address}")
                .buildAndExpand(fireStationAdded.getAddress())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/firestation")
    public void updateFireStation(@RequestBody FireStation fireStation) {
        fireStationDao.savedFireStation(fireStation);
    }

    @DeleteMapping(value = "/firestation")
    public void deleteFireStation(@RequestBody FireStation fireStation) {
        fireStationDao.deleteFireStation(fireStation);

    }
//    @DeleteMapping(value = "/firestation")
//    public void deleteFireStation(@PathVariable Map<String, Integer> queryStringParameters) {
//        String address = String.valueOf(queryStringParameters.get("address"));
//        Integer station = queryStringParameters.get("station");
//
//        fireStationDao.delete(queryStringParameters);
//
//    }


    @PostConstruct
    public void initdata() throws IOException, ParseException {
        fireStationDao.initFireStations();
    }

}
