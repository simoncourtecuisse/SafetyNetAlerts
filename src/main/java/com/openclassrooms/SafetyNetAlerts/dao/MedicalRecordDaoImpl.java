package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.data.Extract;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

@Repository
public class MedicalRecordDaoImpl implements MedicalRecordDao {

    private List<MedicalRecord> medicalRecords;
    private Extract extract = new Extract();

    public List<MedicalRecord> initMedicalRecords(List<Person> persons) throws FileNotFoundException, ParseException {
        this.medicalRecords = extract.extractMedicalRecordsFromJson(persons);
        return medicalRecords;
    }

    @Override
    public List<MedicalRecord> findAll()  {
        return medicalRecords;
    }

    @Override
    public MedicalRecord savedMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecords.add(medicalRecord);
        return medicalRecord;
    }
}
