package com.openclassrooms.SafetyNetAlerts.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;


public interface FireStationDao {

    List<FireStation> initFireStations() throws FileNotFoundException;
    public List<FireStation> findAll() throws FileNotFoundException;
    public FireStation savedFireStation(FireStation fireStation);
    public FireStation deletedFireStation(FireStation fireStation);
    public List<FireStation> getFireStationById (Integer station);
//    void delete(Map<String, Integer> queryStringParameters);
    public JsonArray filterResult (String[] attributs, List<Person> personList) throws JsonProcessingException;
}
