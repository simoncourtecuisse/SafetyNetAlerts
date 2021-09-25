package com.openclassrooms.SafetyNetAlerts.model;

import java.util.ArrayList;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FireStationTest {

    private FireStation fireStationUnderTest;

    @Test
    public void testConstructor() {
        ArrayList<String> stringList = new ArrayList<String>();
        FireStation actualFireStation = new FireStation(stringList, 1);
        ArrayList<String> stringList1 = new ArrayList<String>();
        actualFireStation.setAddressList(stringList1);
        actualFireStation.setStation(1);
        List<String> addressList = actualFireStation.getAddressList();
        assertSame(stringList1, addressList);
        assertEquals(stringList, addressList);
        assertEquals(1, actualFireStation.getStation().intValue());
        assertEquals("FireStation [address=[], station=1]", actualFireStation.toString());
    }

    @Test
    public void testAddAddresses2() {
        FireStation fireStation = new FireStation(new ArrayList<String>(), 1);
        fireStation.addAddresses("42 Main St");
        List<String> addressList = fireStation.getAddressList();
        assertEquals(1, addressList.size());
        assertEquals("42 Main St", addressList.get(0));
    }

    @Test
    public void testEquals() {
        assertFalse((new FireStation(new ArrayList<String>(), 1)).equals(null));
        assertFalse((new FireStation(new ArrayList<String>(), 1)).equals("Different type to FireStation"));
    }

    @Test
    public void testEquals2() {
        FireStation fireStation = new FireStation(new ArrayList<String>(), 1);
        assertTrue(fireStation.equals(fireStation));
        int expectedHashCodeResult = fireStation.hashCode();
        assertEquals(expectedHashCodeResult, fireStation.hashCode());
    }

    @Test
    public void testEquals3() {
        FireStation fireStation = new FireStation(new ArrayList<String>(), 1);
        FireStation fireStation1 = new FireStation(new ArrayList<String>(), 1);

        assertTrue(fireStation.equals(fireStation1));
        int expectedHashCodeResult = fireStation.hashCode();
        assertEquals(expectedHashCodeResult, fireStation1.hashCode());
    }

    @Test
    public void testEquals4() {
        ArrayList<String> stringList = new ArrayList<String>();
        stringList.add("foo");
        FireStation fireStation = new FireStation(stringList, 1);
        assertFalse(fireStation.equals(new FireStation(new ArrayList<String>(), 1)));
    }

    @Test
    public void testEquals5() {
        FireStation fireStation = new FireStation(new ArrayList<String>(), 0);
        assertFalse(fireStation.equals(new FireStation(new ArrayList<String>(), 1)));
    }

    @BeforeEach
    void setUp() {
        fireStationUnderTest = new FireStation(List.of("value"), 0);
    }

    @Test
    void testAddAddresses() {
        fireStationUnderTest.setAddressList(List.of("address"));
    }

    @Test
    void testToString() {
        final String result = fireStationUnderTest.toString();
        assertEquals("FireStation [address=[value], station=0]", result);
    }

    @Test
    void testEqualsAndHashCode() {
        EqualsVerifier.simple().forClass(FireStation.class)
                .verify();
    }
}
