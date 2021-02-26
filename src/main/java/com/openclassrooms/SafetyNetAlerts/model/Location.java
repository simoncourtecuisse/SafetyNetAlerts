package com.openclassrooms.SafetyNetAlerts.model;

public class Location {
	private String address;
	private String city;
	private Integer zip;

	public Location(String address, String city, Integer zip) {
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
