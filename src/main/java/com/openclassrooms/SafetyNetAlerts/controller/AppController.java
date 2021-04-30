package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
import com.openclassrooms.SafetyNetAlerts.data.Extract;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Location;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

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

	@GetMapping(value = "/person")
	public List<Person> allPersons() throws FileNotFoundException {
		return personDao.findAll();
	}

	@GetMapping(value = "/")
	public String safetyNet() {
		return "safetyNet is working";
	}

	@PostMapping(value = "/testpost")
	public String safetyNet2() {
		return "safetyNet is working 2";
	}

	@PostConstruct
	public void initdata() throws IOException, ParseException {

		JsonReader jsonReader = Json.createReader(new FileReader(
				"./src/main/resources/data.json"));

		JsonObject jsonObject = jsonReader.readObject();

		List<Person> persons = Extract.extractPersonsFromJson();
//		List<FireStation> firestations = Extract.extractFireStationsFromJson(jsonObject.getJsonArray("firestations"));
//		List<Location> locations = Extract.extractLocationsFromPersons(jsonObject.getJsonArray("persons"));
//		List<MedicalRecord> medicalrecords = Extract.extractMedicalRecordsFromJson(jsonObject.getJsonArray("medicalrecords"), persons);


	}

}
