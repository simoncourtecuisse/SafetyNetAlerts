package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public interface FireStationDao {

    void initFireStations() throws FileNotFoundException;
    public List<FireStation> findAll() throws FileNotFoundException;
    public FireStation savedFireStation(FireStation fireStation);
}
