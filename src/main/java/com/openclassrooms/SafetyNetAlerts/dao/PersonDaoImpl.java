package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.data.Extract;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Location;
import com.openclassrooms.SafetyNetAlerts.model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.json.JsonObject;
import javax.json.JsonValue;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PersonDaoImpl implements PersonDao {

    private List<Person> persons;
    private Extract extract = new Extract();

    @Autowired
    FireStationDaoImpl fireStationDao;

    public List<Person> initPersons() throws FileNotFoundException {
        this.persons = extract.extractPersonsFromJson();
        return persons;
    }

    @Override
    public List<Person> findAll()  {
//        List<Person> allPersons = extract.extractPersonsFromJson();
        return persons;
    }

    @Override
    public Person savedPerson(Person person) {
        persons.add(person);
        return person;
    }

    @Override
    public Person deletedPerson(Person person) {
        persons.remove(person);
        return person;
    }

    public List<Person> getPersonFromSameStation(Integer station) {
        List<FireStation> fireStation = fireStationDao.getFireStationById(station);
        if (fireStation == null) {
            return new ArrayList<>();
        }
        List<String> listAddress = new ArrayList<>();
        for (FireStation f : fireStation) {
            listAddress.add(f.getAddressList().get(0));
        }
        return persons.stream()
                .filter(p -> (listAddress.contains(p.getLocation().getAddress())))
                .collect(Collectors.toList());
    }

    public List<Person> getPersonEmail(String city) {
        List<Person> personEmail = persons
                .stream()
                .filter(p -> (p.getLocation().getCity() == city))
                .collect(Collectors.toList());
        if (personEmail.size() == 0) return null;
        else return personEmail;
    }

//    public List<Person> getPhoneForAlert(Integer station, Integer phone) {
//        List<FireStation> fireStation = fireStationDao.getFireStationById(station);
//        if (fireStation == null) {
//            return null;
//        }
//        return persons.stream()
//                .filter(p -> (p.getLocation().getAddress().equals(fireStation.getAddressList())) && p.getPhone().equals(fireStation.getAddressList()))
//                .collect(Collectors.toList());
//
//    }


}
