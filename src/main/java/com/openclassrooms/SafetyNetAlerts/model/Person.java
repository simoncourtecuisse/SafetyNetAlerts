package com.openclassrooms.SafetyNetAlerts.model;

public class Person {

	private final String firstName;
	private final String lastName;
	private Location location;
	private String phone;
	private String email;
	private MedicalRecord medicalRecord;

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

	@Override
	public String toString() {
		return "Person [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", location="
				+ location + ", medicalRecord=" + medicalRecord + ", phone=" + phone + "]";
	}

	
}
