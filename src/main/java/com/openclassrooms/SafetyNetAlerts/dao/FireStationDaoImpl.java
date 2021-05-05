package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.data.Extract;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@Repository
public class FireStationDaoImpl implements FireStationDao {

    private List<FireStation> fireStations;
    private Extract extract = new Extract();


    public List<FireStation> initFireStations() throws FileNotFoundException {
        this.fireStations = extract.extractFireStationsFromJson();
        return fireStations;
    }

    @Override
    public List<FireStation> findAll()  {
        return fireStations;
    }

    @Override
    public FireStation savedFireStation(FireStation fireStation) {
        fireStations.add(fireStation);
        return fireStation;
    }

    @Override
    public FireStation deleteFireStation(FireStation fireStation) {
        fireStations.remove(fireStation);
        return fireStation;
    }

//    @Override
//    public void delete(Map<String, Integer> queryStringParameters) {
//        fireStations.remove(fireStation);
//    }
}

