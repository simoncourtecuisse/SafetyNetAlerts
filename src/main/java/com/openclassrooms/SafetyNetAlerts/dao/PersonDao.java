package com.openclassrooms.SafetyNetAlerts.dao;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public interface PersonDao {

    void initPersons(List<Person> persons);
    public List<Person> findAll() throws FileNotFoundException;
    public Person save(Person person);

}
