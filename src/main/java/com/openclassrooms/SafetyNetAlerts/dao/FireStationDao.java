package com.openclassrooms.SafetyNetAlerts.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;

import java.io.FileNotFoundException;
import java.util.List;


public interface FireStationDao {

    List<FireStation> initFireStations() throws FileNotFoundException;

    public List<FireStation> findAll() throws FileNotFoundException;

    public FireStation savedFireStation(FireStation fireStation);

    public FireStation deletedFireStation(FireStation fireStation);

    public List<FireStation> getFireStationById(Integer station);

    public JsonArray filterResult(String[] attributs, List<Person> personList) throws JsonProcessingException;
}
