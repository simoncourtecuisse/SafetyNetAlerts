package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;


public interface MedicalRecordDao {

    List<MedicalRecord> initMedicalRecords(List<Person> persons) throws FileNotFoundException, ParseException;

    public List<MedicalRecord> findAll() throws FileNotFoundException;

    public MedicalRecord savedMedicalRecord(MedicalRecord medicalRecord);

    public MedicalRecord deletedMedicalRecord(MedicalRecord medicalRecord);
}
