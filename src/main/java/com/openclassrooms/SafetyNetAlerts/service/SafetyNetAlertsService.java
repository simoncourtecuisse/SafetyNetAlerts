package com.openclassrooms.SafetyNetAlerts.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.springframework.stereotype.Service;

import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Location;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;

@Service
public class SafetyNetAlertsService {

	private static List<Person> extractPersonsFromJson(JsonArray personsAsJson) {

		List<Person> persons = new ArrayList<>();
		for (JsonValue person : personsAsJson) {
			String firstName = ((JsonObject) person).getString("firstName");
			String lastName = ((JsonObject) person).getString("lastName");
			String address = ((JsonObject) person).getString("address");
			String city = ((JsonObject) person).getString("city");
			String zipAsString = ((JsonObject) person).getString("zip");
			String phone = ((JsonObject) person).getString("phone");
			String email = ((JsonObject) person).getString("email");

			Person ps = new Person(firstName, lastName, address, city, Integer.parseInt(zipAsString), phone, email);
			persons.add(ps);
			System.out.println(ps);
		}
		return persons;
	}

	private static List<FireStation> extractFireStationsFromJson(JsonArray firestationsAsJson) {

		List<FireStation> firestations = new ArrayList<>();
		for (JsonValue firestation : firestationsAsJson) {
			String address = ((JsonObject) firestation).getString("address");
			String stationAsString = ((JsonObject) firestation).getString("station");

			FireStation fs = new FireStation(address, Integer.parseInt(stationAsString));
			firestations.add(fs);
			System.out.println(fs);
		}
		return firestations;
	}

	private static List<Location> extractLocationsFromPersons(JsonArray personsAsJson) {

		List<Location> locations = new ArrayList<>();
		for (JsonValue location : personsAsJson) {
			String address = ((JsonObject) location).getString("address");
			String city = ((JsonObject) location).getString("city");
			String zipAsString = ((JsonObject) location).getString("zip");

			Location lc = new Location(address, city, Integer.parseInt(zipAsString));
			locations.add(lc);
			System.out.println(lc);
		}
		return locations;
		
	}
	private static List<MedicalRecord> extractMedicalRecordsFromJson(JsonArray medicalRecordsAsJson) {

		List<MedicalRecord> medicalRecords = new ArrayList<>();
		for (JsonValue medicalRecord : medicalRecordsAsJson) {
			String firstName = ((JsonObject) medicalRecord).getString("firstName");
			String lastName = ((JsonObject) medicalRecord).getString("lastName");
			String birthDate = ((JsonObject) medicalRecord).getString("birthDate");
			String medications = ((JsonObject) medicalRecord).getString("medications");
			String allergies = ((JsonObject) medicalRecord).getString("allergies");
		
			
			MedicalRecord mr = new MedicalRecord(firstName, lastName, birthDate, medications, allergies);
			medicalRecords.add(mr);
			Person.setMedicalRecord();
			System.out.println(mr);
		}
		return medicalRecords;
	}

//	private static List<Medication> extractMedicationsFromMedicalRecord(JsonArray MedicationsFromMedicalRecord) {
//
//		List<Medication> medications = new ArrayList<>();
//		for (JsonValue medication : MedicationsFromMedicalRecord) {
//			String aznol = ((JsonObject) medication).getString("aznol");
//
//			Medication md = new Medication(aznol);
//			medications.add(md);
//			System.out.println(md);
//		}
//		return medications;
//
//	}
//
//	private static List<Allergy> extractAllergiesFromMedicalRecord(JsonArray AllergiesFromMedicalRecord) {
//		List<Allergy> allergies = new ArrayList<>();
//		for (JsonValue allergy : AllergiesFromMedicalRecord) {
//			String shellfish = ((JsonObject) allergy).getString("shellfish");
//
//			Allergy al = new Allergy(shellfish);
//			allergies.add(al);
//			System.out.println();
//		}
//		return allergies;
//	}

	@PostConstruct
	public void initdata() throws IOException {

		JsonReader jsonReader = Json.createReader(new FileReader(
				"C:/Users/Simon/Desktop/FORMATION DEV JAVA/Projet 5/SafetyNetAlerts/src/main/resources/data.json"));

		JsonObject jsonObject = jsonReader.readObject();

		List<Person> persons = extractPersonsFromJson(jsonObject.getJsonArray("persons"));
		List<FireStation> firestations = extractFireStationsFromJson(jsonObject.getJsonArray("firestations"));
		List<Location> locations = extractLocationsFromPersons(jsonObject.getJsonArray("persons"));
		List<MedicalRecord> medicalrecords = extractMedicalRecordsFromJson(jsonObject.getJsonArray("medicalrecords"));

	}

}

