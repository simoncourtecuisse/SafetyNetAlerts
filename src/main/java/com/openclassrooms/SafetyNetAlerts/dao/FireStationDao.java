package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@Service
public interface FireStationDao {

    List<FireStation> initFireStations() throws FileNotFoundException;
    public List<FireStation> findAll() throws FileNotFoundException;
    public FireStation savedFireStation(FireStation fireStation);

    public FireStation deleteFireStation(FireStation fireStation);
//    void delete(Map<String, Integer> queryStringParameters);
}
