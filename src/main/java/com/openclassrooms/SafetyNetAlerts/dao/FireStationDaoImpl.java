package com.openclassrooms.SafetyNetAlerts.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.openclassrooms.SafetyNetAlerts.data.Extract;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FireStationDaoImpl implements FireStationDao {

    private List<FireStation> fireStations;
    private Extract extract = new Extract();

    public List<FireStation> getFireStationById(Integer station) {
        List<FireStation> fireStationList = fireStations
                .stream()
                .filter(f -> (f.getStation() == station))
                .collect(Collectors.toList());
        if (fireStationList.size() == 0) return null;
        else return fireStationList;
    }

    public List<FireStation> initFireStations() throws FileNotFoundException {
        this.fireStations = extract.extractFireStationsFromJson();
        return fireStations;
    }

    @Override
    public List<FireStation> findAll() {
        return fireStations;
    }

    @Override
    public FireStation savedFireStation(FireStation fireStation) {
        fireStations.add(fireStation);
        return fireStation;
    }

    @Override
    public FireStation deletedFireStation(FireStation fireStation) {
        fireStations.remove(fireStation);
        return fireStation;
    }


    @Override
    public JsonArray filterResult(String[] attributs, List<Person> personList) throws JsonProcessingException {
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept(attributs);
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("dynamicFilter", monFiltre);
        MappingJacksonValue personfilter = new MappingJacksonValue(personList);
        personfilter.setFilters(listDeNosFiltres);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectWriter writer = mapper.writer(listDeNosFiltres);
        String writeValueAsString = writer.writeValueAsString(personfilter);
        System.out.println(writeValueAsString);
        Gson gson = new Gson();
        //JsonElement element = gson.fromJson (jsonStr, JsonElement.class);
//        JsonElement element = new JsonPrimitive(writeValueAsString);
//        JsonObject result = element.getAsJsonObject();
        JsonArray result = gson.fromJson(writeValueAsString, JsonObject.class).getAsJsonArray("value");


        return result;
    }
}


