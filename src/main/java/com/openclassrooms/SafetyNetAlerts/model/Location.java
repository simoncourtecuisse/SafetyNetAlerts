package com.openclassrooms.SafetyNetAlerts.model;
import com.openclassrooms.SafetyNetAlerts.model.Person;

public class Location {
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private Integer zip;
	private String phone;
	private String email;

	public Location(String address, String city, Integer zip) {
		Person person = new Person(firstName, lastName, address, city, zip, phone, email);
		this.address = address;
		this.city = city;
		this.zip = zip;

	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getZip() {
		return zip;
	}

	public void setZip(Integer zip) {
		this.zip = zip;
	}

}
