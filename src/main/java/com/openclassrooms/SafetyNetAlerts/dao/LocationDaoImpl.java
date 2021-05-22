package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.data.Extract;
import com.openclassrooms.SafetyNetAlerts.model.Location;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LocationDaoImpl implements LocationDao {

    private List<Location> locations;
    private Extract extract = new Extract();

    @Autowired
    private PersonDao personDao;

    public List<Location> initLocations(List<Person> persons) throws FileNotFoundException {

        Map<Location, List<Person>> mappingLocation = new HashMap<>();
        for (Person p : personDao.findAll()) {
           if (mappingLocation.get(p.getLocation()) != null) {
                mappingLocation.get(p.getLocation()).add(p);
            }
           else {
               List<Location> mappingLocationList = new ArrayList<>();
               mappingLocationList.add(p.getLocation());
           }
        }

        System.out.println(mappingLocation);
        return locations;
    }
}
//    for (Person p : personDao.findAll()) {
//        if (mapping.get(p.getLocation())) {
//
//        mapping.get(p.getLocation()).add(p);
//        else {