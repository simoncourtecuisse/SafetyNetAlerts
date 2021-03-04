package com.openclassrooms.SafetyNetAlerts.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;


public class MedicalRecord {

	private final String firstName;
	private final String lastName;
	private final Date birthDate;
	private String medications;
	private String allergies;

	public MedicalRecord(String firstName, String lastName, Date birthDate, String medications,
			String allergies) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.medications = medications;
		this.allergies = allergies;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public String getMedications() {
		return medications;
	}

	public void setMedications(String medications) {
		this.medications = medications;
	}

	public String getAllergies() {
		return allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	public class AgeCalculator{
		public int calculateAge(LocalDate birthDate, LocalDate currentDate) {
			if ((birthDate != null) && (currentDate != null)) {
				return Period.between(birthDate, currentDate).getYears();
			} else {
				return 0;
			}
		}
	}
	
}
