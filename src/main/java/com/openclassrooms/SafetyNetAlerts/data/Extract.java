package com.openclassrooms.SafetyNetAlerts.data;

import com.openclassrooms.SafetyNetAlerts.dao.MedicalRecordDao;
import com.openclassrooms.SafetyNetAlerts.dao.PersonDao;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Location;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.json.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class Extract {
    public static JsonReader jsonReader;

    static {
        try {
            jsonReader = Json.createReader(new FileReader(
                    "./src/main/resources/data.json"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static JsonObject jsonObject = jsonReader.readObject();

    @Autowired
    private PersonDao personDao;
    @Autowired
    private MedicalRecordDao medicalRecordDao;

    public static List<Person> extractPersonsFromJson() throws FileNotFoundException {


        List<Person> persons = new ArrayList<>();
        for (JsonValue person : jsonObject.getJsonArray("persons")) {
            String firstName = ((JsonObject) person).getString("firstName");
            String lastName = ((JsonObject) person).getString("lastName");
            String address = ((JsonObject) person).getString("address");
            String city = ((JsonObject) person).getString("city");
            String zipAsString = ((JsonObject) person).getString("zip");
            String phone = ((JsonObject) person).getString("phone");
            String email = ((JsonObject) person).getString("email");

            Person ps = new Person(firstName, lastName, address, city, Integer.parseInt(zipAsString), phone, email);
            persons.add(ps);
            System.out.println(ps);
        }
        return persons;
    }

    public static List<FireStation> extractFireStationsFromJson() {

        List<FireStation> firestations = new ArrayList<>();
        for (JsonValue firestation : jsonObject.getJsonArray("firestations")) {
            String address = ((JsonObject) firestation).getString("address");
            String stationAsString = ((JsonObject) firestation).getString("station");

            FireStation fs = new FireStation(Collections.singletonList(address), Integer.parseInt(stationAsString));
            firestations.add(fs);
            System.out.println(fs);

        }
        return firestations;

    }

    public static List<Location> extractLocationsFromPersons(JsonArray personsAsJson) {

        List<Location> locations = new ArrayList<>();
        for (JsonValue location : personsAsJson) {
            String address = ((JsonObject) location).getString("address");
            String city = ((JsonObject) location).getString("city");
            String zipAsString = ((JsonObject) location).getString("zip");

            Location lc = new Location(address, city, Integer.parseInt(zipAsString));
            locations.add(lc);
            System.out.println(lc);
        }
        return locations;

    }

    public static List<MedicalRecord> extractMedicalRecordsFromJson(List<Person> persons)
            throws ParseException {

        List<MedicalRecord> medicalRecords = new ArrayList<>();
        for (JsonValue medicalRecord : jsonObject.getJsonArray("medicalrecords")) {
            String firstName = ((JsonObject) medicalRecord).getString("firstName");
            String lastName = ((JsonObject) medicalRecord).getString("lastName");
            String birthdate = ((JsonObject) medicalRecord).getString("birthdate");

            Person matchingObject = persons.stream()
                    .filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
                    .findAny().orElse(null);
            matchingObject.addMedications(((JsonObject) medicalRecord).getJsonArray("medications").toString());
            matchingObject.addAllergies(((JsonObject) medicalRecord).getJsonArray("allergies").toString());
            matchingObject.setBirthdate(LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            System.out.println(matchingObject);
        }

        return medicalRecords;
    }


    private static List<String> extractMedicationsFromMedicalRecord(JsonArray MedicationsFromMedicalRecord) {

        List<String> medications = new ArrayList<>();
        for (JsonValue medication : MedicationsFromMedicalRecord) {
            String md = medication.toString();
            medications.add(md);
            System.out.println(md);
        }
        return medications;

    }

    private static List<String> extractAllergiesFromMedicalRecord(JsonArray AllergiesFromMedicalRecord) {
        List<String> allergies = new ArrayList<>();
        for (JsonValue allergy : AllergiesFromMedicalRecord) {
            String al = allergy.toString();
            allergies.add(al);
            System.out.println(al);
        }
        return allergies;
    }

    @PostConstruct
    public void initdata() throws IOException, ParseException {
        List<Person> persons = personDao.initPersons();
        medicalRecordDao.initMedicalRecords(persons);
    }
}

