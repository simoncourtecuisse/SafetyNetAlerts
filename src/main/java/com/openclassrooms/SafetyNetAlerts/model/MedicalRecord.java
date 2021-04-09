package com.openclassrooms.SafetyNetAlerts.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;


public class MedicalRecord {

	private String firstName;
	private String lastName;

	private List<String> medications;
	private List<String> allergies;

	public MedicalRecord(String firstName, String lastName, Date birthdate, List<String> medications,
	List<String> allergies) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.medications = medications;
		this.allergies = allergies;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public List getMedications() {
		return medications;
	}

	public void setMedications(List<String> medications) {
		this.medications = medications;
	}

	public List getAllergies() {
		return allergies;
	}

	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}
	
	



	
}
