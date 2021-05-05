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

//    @Override
//    public void addPerson(String simon, String courtecuisse) {
//        persons.add(new Person("vs", "vs", "vs", "vs", 12, "vs", "vs"));
//    }
}
