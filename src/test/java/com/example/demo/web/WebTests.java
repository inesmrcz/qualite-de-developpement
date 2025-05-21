package com.example.demo.web;

import com.example.demo.data.Voiture;
import com.example.demo.service.Echantillon;
import com.example.demo.service.StatistiqueImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WebTests {

    @MockBean
    StatistiqueImpl statistiqueImpl;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void statistique_test() throws Exception {
        doNothing().when(statistiqueImpl).ajouter(any(Voiture.class));
        when(statistiqueImpl.prixMoyen()).thenReturn(new Echantillon(2, 25_000));

        mockMvc.perform(get("/statistique"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreDeVoitures").value(2))
                .andExpect(jsonPath("$.prixMoyen").value(25_000))
                .andReturn();
    }

    @Test
    public void voiture_test() throws Exception {
        mockMvc.perform(post("/voiture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"marque\": \"Audi\", \"prix\": 50000 }")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(statistiqueImpl, times(1)).ajouter(any(Voiture.class));
    }

    @Test
    public void voiture_test_exception() throws Exception {
        doNothing().when(statistiqueImpl).ajouter(any(Voiture.class));
        when(statistiqueImpl.prixMoyen()).thenThrow(new ArithmeticException("Erreur calcul moyen"));

        mockMvc.perform(get("/statistique"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

}