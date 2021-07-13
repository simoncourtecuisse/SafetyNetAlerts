package com.openclassrooms.SafetyNetAlerts.dao;

import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MedicalRecordDaoImplTest {

    private MedicalRecordDaoImpl medicalRecordDaoImplUnderTest;

    @BeforeEach
    void setUp() {
        medicalRecordDaoImplUnderTest = new MedicalRecordDaoImpl();
    }

    @Test
    void testInitMedicalRecords() throws Exception {
        // Setup
        final List<Person> persons = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));
        final MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setMedications(List.of("value"));
        medicalRecord.setAllergies(List.of("value"));
        final List<MedicalRecord> expectedResult = List.of(medicalRecord);

        // Run the test
        final List<MedicalRecord> result = medicalRecordDaoImplUnderTest.initMedicalRecords(persons);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testInitMedicalRecords_ThrowsFileNotFoundException() {
        // Setup
        final List<Person> persons = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));

        // Run the test
        assertThrows(FileNotFoundException.class, () -> medicalRecordDaoImplUnderTest.initMedicalRecords(persons));
    }

    @Test
    void testInitMedicalRecords_ThrowsParseException() {
        // Setup
        final List<Person> persons = List.of(new Person("firstName", "lastName", "address", "city", 0, "phone", "email"));

        // Run the test
        assertThrows(ParseException.class, () -> medicalRecordDaoImplUnderTest.initMedicalRecords(persons));
    }

    @Test
    void testFindAll() {
        // Setup
        final MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setMedications(List.of("value"));
        medicalRecord.setAllergies(List.of("value"));
        final List<MedicalRecord> expectedResult = List.of(medicalRecord);

        // Run the test
        final List<MedicalRecord> result = medicalRecordDaoImplUnderTest.findAll();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testSavedMedicalRecord() {
        // Setup
        final MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setMedications(List.of("value"));
        medicalRecord.setAllergies(List.of("value"));

        final MedicalRecord expectedResult = new MedicalRecord();
        expectedResult.setMedications(List.of("value"));
        expectedResult.setAllergies(List.of("value"));

        // Run the test
        final MedicalRecord result = medicalRecordDaoImplUnderTest.savedMedicalRecord(medicalRecord);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testDeletedMedicalRecord() {
        // Setup
        final MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setMedications(List.of("value"));
        medicalRecord.setAllergies(List.of("value"));

        final MedicalRecord expectedResult = new MedicalRecord();
        expectedResult.setMedications(List.of("value"));
        expectedResult.setAllergies(List.of("value"));

        // Run the test
        final MedicalRecord result = medicalRecordDaoImplUnderTest.deletedMedicalRecord(medicalRecord);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
