package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.data.Extract;
import com.openclassrooms.SafetyNetAlerts.model.Person;

import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.util.List;

@Repository
public class PersonDaoImpl implements PersonDao {

    private List<Person> persons;
    private Extract extract = new Extract();

    public void initPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public List<Person> findAll() throws FileNotFoundException {
        List<Person> allPerosns = extract.extractPersonsFromJson();
        return allPerosns;
    }

    @Override
    public Person save(Person person) {
        return person;
    }
}
