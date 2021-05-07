package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public interface FireStationDao {

    List<FireStation> initFireStations() throws FileNotFoundException;
    public List<FireStation> findAll() throws FileNotFoundException;
    public FireStation savedFireStation(FireStation fireStation);
    public FireStation deletedFireStation(FireStation fireStation);
//    void delete(Map<String, Integer> queryStringParameters);
}
