package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.data.Extract;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.util.List;

@Repository
public class FireStationDaoImpl implements FireStationDao {

    private List<FireStation> fireStations;
    private Extract extract = new Extract();

    public void initFireStations() throws FileNotFoundException {
        this.fireStations = extract.extractFireStationsFromJson();
    }

    @Override
    public List<FireStation> findAll()  {
        return fireStations;
    }

    @Override
    public FireStation savedFireStation(FireStation fireStation) {
        return fireStation;
    }
}

