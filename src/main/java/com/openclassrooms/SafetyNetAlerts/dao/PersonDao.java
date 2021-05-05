package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public interface PersonDao {

    List<Person> initPersons() throws FileNotFoundException;
    public List<Person> findAll() throws FileNotFoundException;
    public Person savedPerson(Person person);

}
