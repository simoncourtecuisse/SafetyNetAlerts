package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

@Service
public interface MedicalRecordDao {
    void initMedicalRecords(List<Person> persons) throws FileNotFoundException, ParseException;
    public List<MedicalRecord> findAll() throws FileNotFoundException;
    public MedicalRecord savedMedicalRecord(MedicalRecord medicalRecord);
}
