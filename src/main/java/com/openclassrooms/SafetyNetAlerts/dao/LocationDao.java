package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.model.Location;
import com.openclassrooms.SafetyNetAlerts.model.Person;

import java.io.FileNotFoundException;
import java.util.List;


public interface LocationDao {
    List<Location> initLocations(List<Person> persons) throws FileNotFoundException;
}
