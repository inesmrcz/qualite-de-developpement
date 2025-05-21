package com.example.demo.service;

import com.example.demo.data.Voiture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StatistiqueTests {

    @Test
    void testPrixMoyenAvecMockito() {
        Statistique statistique = new StatistiqueImpl();

        Voiture voiture1 = mock(Voiture.class);
        Voiture voiture2 = mock(Voiture.class);
        Voiture voiture3 = mock(Voiture.class);

        when(voiture1.getPrix()).thenReturn(10000);
        when(voiture2.getPrix()).thenReturn(20750);
        when(voiture3.getPrix()).thenReturn(33999);

        statistique.ajouter(voiture1);
        statistique.ajouter(voiture2);
        statistique.ajouter(voiture3);

        Echantillon resultat = statistique.prixMoyen();

        assertEquals(3, resultat.getNombreDeVoitures());
        assertEquals((10000 + 20750 + 33999) / 3, resultat.getPrixMoyen());

        verify(voiture1, times(1)).getPrix();
        verify(voiture2, times(1)).getPrix();
        verify(voiture3, times(1)).getPrix();
    }

    @Test
    void testPrixMoyenListeVide() {
        Statistique statistique = new StatistiqueImpl();

        assertThrows(ArithmeticException.class, () -> statistique.prixMoyen());
    }
}