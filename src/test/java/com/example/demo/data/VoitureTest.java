package com.example.demo.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VoitureTest {

    @Test
    void testConstructeurVide() {
        Voiture v = new Voiture();
        assertNull(v.getMarque());
        assertEquals(0, v.getPrix());
        assertEquals(0, v.getId());
    }

    @Test
    void testConstructeurAvecArguments() {
        Voiture v = new Voiture("Mercedes", 20000);
        assertEquals("Mercedes", v.getMarque());
        assertEquals(20000, v.getPrix());
        assertEquals(0, v.getId());
    }

    @Test
    void testSettersEtGetters() {
        Voiture v = new Voiture();
        v.setMarque("Peugeot");
        v.setPrix(15000);
        v.setId(3);

        assertEquals("Peugeot", v.getMarque());
        assertEquals(15000, v.getPrix());
        assertEquals(3, v.getId());
    }

    @Test
    void testToString() {
        Voiture v = new Voiture("Renault", 18000);
        v.setId(5);
        String attendu = "Car{marque='Renault', prix=18000, id=5}";
        assertEquals(attendu, v.toString());
    }
}
