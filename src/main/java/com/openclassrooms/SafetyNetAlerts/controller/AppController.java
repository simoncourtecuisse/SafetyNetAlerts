package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.dao.FireStationDao;
import com.openclassrooms.SafetyNetAlerts.dao.MedicalRecordDao;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AppController {

//	@GetMapping(value = "/person")
//	public Person getPerson(String firstName, String lastName, String address, String city, Integer zip, String phone,
//							String email) {
//		var person = new Person(firstName, lastName, address, city, zip, phone,
//				email);
//		return person;
//	}

	@Autowired
	private PersonDao personDao;

	@Autowired
	private FireStationDao fireStationDao;

	@Autowired
	private MedicalRecordDao medicalRecordDao;

	@GetMapping(value = "/person")
	public List<Person> allPersons() throws FileNotFoundException {
		return personDao.findAll();
	}

	@GetMapping(value = "/personInfo")
	public List<Person> getPerson(@RequestParam Map<String, String> queryStringParameters) throws FileNotFoundException {
		String firstName = queryStringParameters.get("firstName");
		String lastName = queryStringParameters.get("lastName");
//		return personDao.findAll().stream()
//				.filter(p -> (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) || (p.getLastName().equals(lastName)))
//				.collect(Collectors.toList());
		List<Person> persons = personDao.findAll();
		if (firstName != null && lastName != null) {
			return persons.stream()
					.filter(p -> (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)))
					.collect(Collectors.toList());
		} else {
			return persons.stream()
					.filter(p -> (p.getLastName().equals(lastName)))
					.collect(Collectors.toList());
		}
	}


	@GetMapping(value = "/personInfo/{id}")
	public List<Person> getPerson(@PathVariable(value = "id") String id, @RequestParam Map<String, String> queryStringParameters) throws FileNotFoundException {
		String[] names = id.split("-");
		return personDao.findAll();
	}

//	@GetMapping(value = "/person/{lastname}/{firstname}")
//	public Person getPerson(@PathVariable(value = "lastname") String lastname, @PathVariable(value = "firstname") String firstname) throws FileNotFoundException {
//		List<Person> allPersons = personDao.findAll();
//		for (Person p : allPersons) {
//			if (p.getLastName().toLowerCase().equals(lastname.toLowerCase()) && p.getFirstName().toLowerCase().equals(firstname.toLowerCase())) {
//				return p;
//			}
//		}
//		return null;
//	}

	@GetMapping(value = "/firestation")
	public List<FireStation> allFireStations() throws FileNotFoundException {
		return fireStationDao.findAll();
	}

	@GetMapping(value = "/medicalRecord")
	public List<MedicalRecord> allMedicalRecords() throws FileNotFoundException {
		return personDao.findAll().stream().map(person->person.getMedicalRecord()).collect(Collectors.toList());
	}

	@PostMapping(value = "/testpost")
	public String safetyNet2() {
		return "safetyNet is working 2";
	}

	@PostConstruct
	public void initdata() throws IOException, ParseException {
		List<Person> persons = personDao.initPersons();
		fireStationDao.initFireStations();
		medicalRecordDao.initMedicalRecords(persons);
	}

}
