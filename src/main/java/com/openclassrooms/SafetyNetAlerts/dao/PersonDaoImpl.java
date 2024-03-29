package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.data.Extract;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PersonDaoImpl implements PersonDao {

    @Autowired
    FireStationDaoImpl fireStationDao;

    private List<Person> persons = new ArrayList<>();
    private Extract extract = new Extract();

    public List<Person> initPersons() throws FileNotFoundException {
        this.persons = extract.extractPersonsFromJson();
        return persons;
    }

    @Override
    public List<Person> findAll() {
//        List<Person> allPersons = extract.extractPersonsFromJson();
        return getPersons();
    }

    @Override
    public Person savedPerson(Person person) {
        getPersons().add(person);
        return person;
    }

    @Override
    public boolean deletedPerson1(Person person) {
        Person matchingPerson = getPersons().stream()
                .filter(p -> (p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName())))
                .findAny().orElse(null);
        System.out.println(matchingPerson);
        if (matchingPerson == null) return false;
        getPersons().remove(matchingPerson);
        return true;
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
        return getPersons().stream()
                .filter(p -> (listAddress.contains(p.getLocation().getAddress())))
                .collect(Collectors.toList());
    }

    public List<Person> getPersonEmail(String city) {
        List<Person> personEmail = getPersons()
                .stream()
                .filter(p -> (p.getLocation().getCity() == city))
                .collect(Collectors.toList());
        if (personEmail.size() == 0) return null;
        else return personEmail;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
