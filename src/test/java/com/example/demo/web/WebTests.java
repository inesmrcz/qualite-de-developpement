package com.example.demo.web;

import com.example.demo.service.Echantillon;
import com.example.demo.service.PasDeVoitureException;
import com.example.demo.service.StatistiqueImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WebTests {

    @MockBean
    StatistiqueImpl statistiqueImpl;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testAjoutVoitureTesla() throws Exception {
        String voitureJson = "{\"marque\":\"Tesla\",\"prix\":49999}";

        mockMvc.perform(post("/voiture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voitureJson))
                .andExpect(status().isOk());

        verify(statistiqueImpl, times(1)).ajouter(argThat(voiture ->
                "Tesla".equals(voiture.getMarque()) && voiture.getPrix() == 49999));
    }

    @Test
    void testAjoutVoiturePeugeot() throws Exception {
        String voitureJson = "{\"marque\":\"Peugeot\",\"prix\":22000}";

        mockMvc.perform(post("/voiture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voitureJson))
                .andExpect(status().isOk());

        verify(statistiqueImpl, times(1)).ajouter(argThat(voiture ->
                "Peugeot".equals(voiture.getMarque()) && voiture.getPrix() == 22000));
    }

    @Test
    void testGetStatistiquesAvecValeurs() throws Exception {
        Echantillon echantillon = new Echantillon(7, 31500);
        when(statistiqueImpl.prixMoyen()).thenReturn(echantillon);

        mockMvc.perform(get("/statistique"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreDeVoitures").value(7))
                .andExpect(jsonPath("$.prixMoyen").value(31500));
    }

    @Test
    void testGetStatistiquesVide() throws Exception {
        when(statistiqueImpl.prixMoyen()).thenThrow(new ArithmeticException());

        mockMvc.perform(get("/statistique"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testAjoutVoitureAvecJsonInvalide() throws Exception {
        String voitureJsonInvalide = "{\"modele\":\"Clio\",\"prix\":15000}";

        mockMvc.perform(post("/voiture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voitureJsonInvalide))
                .andExpect(status().isBadRequest());
    }
}
