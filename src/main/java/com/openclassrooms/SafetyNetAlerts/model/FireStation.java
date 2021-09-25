package com.openclassrooms.SafetyNetAlerts.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FireStation {

	private List<String> addressList = new ArrayList<>();
	private Integer station;
	private Location location;

	public FireStation(List<String> addressList, Integer station) {
		this.addressList = addressList;
		this.station = station;
	}

	public void addAddresses(String address) {
		addressList.add(address);
	}

	public List<String> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
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
		return Objects.equals(addressList, that.addressList) && Objects.equals(station, that.station) && Objects.equals(location, that.location);
	}

	@Override
	public int hashCode() {
		return Objects.hash(addressList, station, location);
	}

	@Override
	public String toString() {
		return "FireStation [address=" + addressList + ", station=" + station + "]";
	}
}