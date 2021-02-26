package com.openclassrooms.SafetyNetAlerts.model;

import java.util.Date;
import java.util.List;

public class MedicalRecord {

	private final String firstName;
	private final String lastName;
	private final Date birthdate;
	private List<Medication> medications;
	private List<Allergy> allergies;

	public MedicalRecord(String firstName, String lastName, Date birthdate, List<Medication> medications,
			List<Allergy> allergies) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.medications = medications;
		this.allergies = allergies;
	}

	public List<Medication> getMedications() {
		return medications;
	}

	public void setMedications(List<Medication> medications) {
		this.medications = medications;
	}

	public List<Allergy> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<Allergy> allergies) {
		this.allergies = allergies;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getBirthdate() {
		return birthdate;
	}
}
