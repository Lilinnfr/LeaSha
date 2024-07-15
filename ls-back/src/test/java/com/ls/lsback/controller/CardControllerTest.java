package com.ls.lsback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ls.lsback.entity.CardEntity;
import com.ls.lsback.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    private CardEntity card;

    @BeforeEach
    void setUp() {
        card = new CardEntity();
        card.setId(1L);
        card.setRecto("Card Front");
        card.setVerso("Card Back");
    }

    @Test
    @WithMockUser(username = "testuser", password = "password")
    public void testListCardsByMemoId() throws Exception {
        List<CardEntity> cardList = new ArrayList<>();
        cardList.add(card);

        when(cardService.listCartesByMemo(anyLong())).thenReturn(cardList);

        mockMvc.perform(MockMvcRequestBuilders.get("/memoCarte/carte/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].recto").value("Card Front"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].verso").value("Card Back"))
                .andDo(print());

        Mockito.verify(cardService).listCartesByMemo(anyLong());
    }

    @Test
    @WithMockUser(username = "testuser", password = "password")
    public void testGetCardById() throws Exception {
        when(cardService.getCarte(anyLong())).thenReturn(card);

        mockMvc.perform(MockMvcRequestBuilders.get("/memoCarte/carte/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.recto").value("Card Front"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.verso").value("Card Back"))
                .andDo(print());

        Mockito.verify(cardService).getCarte(anyLong());
    }

    @Test
    @WithMockUser(username = "testuser", password = "password")
    public void testCreateCard() throws Exception {
        CardEntity newCard = new CardEntity();
        newCard.setId(1L);
        newCard.setRecto("New Card Front");
        newCard.setVerso("New Card Back");

        when(cardService.addCarte(any(CardEntity.class), anyLong())).thenReturn(newCard);

        ObjectMapper objectMapper = new ObjectMapper();
        String cardJson = objectMapper.writeValueAsString(newCard);

        mockMvc.perform(MockMvcRequestBuilders.post("/memoCarte/carte/creation")
                        .param("memoId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cardJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.recto").value("New Card Front"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.verso").value("New Card Back"))
                .andDo(print());

        Mockito.verify(cardService).addCarte(any(CardEntity.class), anyLong());
    }

    @Test
    @WithMockUser(username = "testuser", password = "password")
    public void testUpdateCard() throws Exception {
        CardEntity updatedCard = new CardEntity();
        updatedCard.setId(1L);
        updatedCard.setRecto("Updated Card Front");
        updatedCard.setVerso("Updated Card Back");

        when(cardService.updateCarte(anyLong(), any(CardEntity.class))).thenReturn(updatedCard);

        ObjectMapper objectMapper = new ObjectMapper();
        String cardJson = objectMapper.writeValueAsString(updatedCard);

        mockMvc.perform(MockMvcRequestBuilders.put("/memoCarte/carte/modification/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cardJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.recto").value("Updated Card Front"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.verso").value("Updated Card Back"))
                .andDo(print());

        Mockito.verify(cardService).updateCarte(anyLong(), any(CardEntity.class));
    }

    @Test
    @WithMockUser(username = "testuser", password = "password")
    public void testDeleteCardById() throws Exception {
        when(cardService.deleteCarte(anyLong())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/memoCarte/carte/suppression/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(print());

        Mockito.verify(cardService).deleteCarte(anyLong());
    }
}