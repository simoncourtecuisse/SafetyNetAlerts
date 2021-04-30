package com.openclassrooms.SafetyNetAlerts.model;

public class FireStation {

	private String address;
	private Integer station;
	private Location location;

	public FireStation(String address, Integer station) {
		this.address = address;
		this.station = station;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStation() {
		return station;
	}

	public void setStation(Integer station) {
		this.station = station;
	}

	@Override
	public String toString() {
		return "FireStation [address=" + address + ", station=" + station + "]";
	}
}
