package com.openclassrooms.SafetyNetAlerts.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Person {

	private final String firstName;
	private final String lastName;
	private Location location;
	private String phone;
	private String email;
	private MedicalRecord medicalRecord = new MedicalRecord();
	private LocalDate birthdate;

	public Person(String firstName, String lastName, String address, String city, Integer zip, String phone,
			String email) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.location = new Location(address, city, zip);
		this.phone = phone;
		this.email = email;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}


	public void addMedications(String medication) {
		medicalRecord.addMedications(medication);
	}

	public void addAllergies(String allergy) {
		medicalRecord.addAllergies(allergy);
	}

	public int calculateAge(LocalDate birthDate, LocalDate currentDate) {
		if ((birthDate != null) && (currentDate != null)) {
			return Period.between(birthDate, currentDate).getYears();
		} else {
			return 0;
		}
	}

	public int getAge() {
		return calculateAge(birthdate, LocalDate.now());
	}

	@Override
	public String toString() {
		return "Person [FirstName = " + firstName + ", LastName = " + lastName + ", Birthdate = " + birthdate + ", Age = " + calculateAge(birthdate, LocalDate.now()) + ", Location = "
				+ location + ", Phone = " + phone + ", Email = " + email + ", Medications = " + medicalRecord.getMedications() + ", Allergies = " + medicalRecord.getAllergies() + "]";
	}


	
}
