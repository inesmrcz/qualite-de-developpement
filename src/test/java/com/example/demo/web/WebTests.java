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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WebTests {

    @MockBean
    StatistiqueImpl statistique1;


    @Autowired
    MockMvc mockMvc;

    @Test
    public void getTest() throws Exception {
        

        
        Echantillon mockEchantillon = new Echantillon();
        mockEchantillon.setNombreDeVoitures(2);
        mockEchantillon.setPrixMoyen(1000);
        
        when(statistique1.prixMoyen()).thenReturn(mockEchantillon);



        mockMvc.perform(get("/statistique"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreDeVoitures").value(2))
                .andExpect(jsonPath("$.prixMoyen").value(1000))
                .andReturn();
                
    
    }

    @Test
    public void getTestDEP() throws Exception {
        String voitureJson = "{ \"id\": 1, \"marque\": \"Peugeot\", \"prix\": 18000 }";

        mockMvc.perform(post("/voiture")
                .contentType(MediaType.APPLICATION_JSON)
                .content(voitureJson))
                .andExpect(status().isOk());
    
        

}

        @Test
        public void getTestDEPht() throws Exception {
            when(statistique1.prixMoyen()).thenThrow(new ArithmeticException());

            mockMvc.perform(get("/statistique"))
                    .andDo(print())
                    .andExpect(status().is4xxClientError())
                    .andReturn();

            //

}

   
}