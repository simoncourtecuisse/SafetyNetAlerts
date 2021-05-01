package com.openclassrooms.SafetyNetAlerts.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MedicalRecord {

	private List<String> medications = new ArrayList<>();
	private List<String> allergies = new ArrayList<>();

	public List getMedications() {
		return medications;
	}

	public void addMedications(String medication) {
		medications.add(medication);
	}

	public List getAllergies() {
		return allergies;
	}

	public void addAllergies(String allergy) {
		allergies.add(allergy);
	}







}
