package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.model.Person;

import java.io.FileNotFoundException;
import java.util.List;


public interface PersonDao {

    List<Person> initPersons() throws FileNotFoundException;

    public List<Person> findAll() throws FileNotFoundException;

    public Person savedPerson(Person person);

    public boolean deletedPerson1(Person person);

    public List<Person> getPersonFromSameStation(Integer station);

    public List<Person> getPersonEmail(String city);


}
