package com.openclassrooms.SafetyNetAlerts.model;

import java.util.Objects;

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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof FireStation)) return false;
		FireStation that = (FireStation) o;
		return Objects.equals(address, that.address) && Objects.equals(station, that.station) && Objects.equals(location, that.location);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, station, location);
	}

	@Override
	public String toString() {
		return "FireStation [address=" + address + ", station=" + station + "]";
	}
}

//	Person matchingObject = persons.stream().
//			filter(p -> p.getAddress(address).equals(address)).
//			findAny().orElse(null);