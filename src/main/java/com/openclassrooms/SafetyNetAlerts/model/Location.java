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


	@Override
	public String toString() {
		return "Location [address=" + address + ", city=" + city + ", zip=" + zip + "]";
	}

	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Location)) {
            return false;
        }

        Location other = (Location) obj;
        return (address.equals(other.address) && city.equals(other.city) && zip.equals(other.zip)) ;

    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
		return result;
	}


}
