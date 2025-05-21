package com.example.demo.service;

import com.example.demo.data.Voiture;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StatistiqueTests {

    @MockBean
    StatistiqueImpl statistiqueImpl;

    @Test
    public void testAjouterNouvelleVoiture() {
        Voiture voiture = new Voiture("Peugeot", 19500);
        statistiqueImpl.ajouter(voiture);
        verify(statistiqueImpl, times(1)).ajouter(voiture);
    }

    @Test
    public void testPrixMoyenReel() {
        Statistique statistique = new StatistiqueImpl();

        Voiture voiture1 = new Voiture("Renault", 14000);
        Voiture voiture2 = new Voiture("Audi", 34000);

        statistique.ajouter(voiture1);
        statistique.ajouter(voiture2);

        Echantillon echantillon = statistique.prixMoyen();

        assertEquals(2, echantillon.getNombreDeVoitures());
        assertEquals(24000, echantillon.getPrixMoyen());
    }

    @Test
    public void testGetPrixSimple(){
        Voiture voiture = new Voiture("Citroën", 18750);
        assertEquals(18750, voiture.getPrix());
    }

    @Test
    public void testPrixMoyenAvecMock(){
        when(statistiqueImpl.prixMoyen()).thenReturn(new Echantillon(4, 22500));

        Echantillon resultat = statistiqueImpl.prixMoyen();

        assertEquals(22500, resultat.getPrixMoyen());
        assertEquals(4, resultat.getNombreDeVoitures());

        verify(statistiqueImpl, times(1)).prixMoyen();
    }

    @Test
    public void testPrixMoyenListeVide() {
        Statistique statistique = new StatistiqueImpl();
        assertThrows(ArithmeticException.class, statistique::prixMoyen);
    }
}