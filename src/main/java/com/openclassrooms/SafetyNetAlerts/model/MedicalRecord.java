package com.openclassrooms.SafetyNetAlerts.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MedicalRecord)) return false;
        MedicalRecord that = (MedicalRecord) o;
        return Objects.equals(medications, that.medications) && Objects.equals(allergies, that.allergies);
    }

    @Override
    public String toString() {
        return "medications = " + medications + ", allergies = " + allergies;
    }

    @Override
    public int hashCode() {
        return Objects.hash(medications, allergies);
    }
}
