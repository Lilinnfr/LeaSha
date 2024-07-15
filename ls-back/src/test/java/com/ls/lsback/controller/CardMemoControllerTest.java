package com.ls.lsback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ls.lsback.entity.CardMemoEntity;
import com.ls.lsback.service.CardMemoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;


@SpringBootTest
@AutoConfigureMockMvc
public class CardMemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardMemoService cardMemoService;

    @Test
    public void testListCardMemo() throws Exception {
        List<CardMemoEntity> listMemo = new ArrayList<>();
        Mockito.when(cardMemoService.listMemoCarte(anyString())).thenReturn(listMemo);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/memoCarte/Mes mémos cartes").
                         with(user("testuser").password("password")).
                         contentType(MediaType.APPLICATION_JSON)).
                 andExpect(status().isNoContent()).
                 andDo(print()).
                 andReturn();

        Mockito.verify(cardMemoService).listMemoCarte(anyString());
    }

    @Test
    public void testGetCardMemoById() throws Exception {
        // on crée un objet CardMemoEntity avec des valeurs définies
        CardMemoEntity memo = new CardMemoEntity();
        memo.setId(1L);
        memo.setTitre("Title");
        memo.setDescription("Description");

        // on configure Mockito pour retourner cet objet
        Mockito.when(cardMemoService.getMemoCarte(anyLong())).thenReturn(memo);

        // on effectue la requête et vérifie la réponse
        mockMvc.perform(MockMvcRequestBuilders.get("/memoCarte/1").
                         with(user("testuser").password("password")).
                         contentType(MediaType.APPLICATION_JSON)).
                 andExpect(status().isOk()).
                 andExpect(jsonPath("$.id").value(1)).// on vérifie que l'id a la bonne valeur
                 andExpect(jsonPath("$.titre").value("Title")).
                 andDo(print());

        Mockito.verify(cardMemoService).getMemoCarte(anyLong());
    }

    @Test
    public void testCreateCardMemo() throws Exception {
        CardMemoEntity memo = new CardMemoEntity();
        Mockito.when(cardMemoService.addMemoCarte(Mockito.any(CardMemoEntity.class))).thenReturn(memo);

        ObjectMapper objectMapper = new ObjectMapper();
        String memoJson = objectMapper.writeValueAsString(memo);

        mockMvc.perform(MockMvcRequestBuilders.post("/memoCarte/creation").
                         with(user("testuser").password("password")).
                         contentType(MediaType.APPLICATION_JSON).
                         content(memoJson)).
                 andExpect(status().isCreated()).
                 andDo(print());

        Mockito.verify(cardMemoService).addMemoCarte(Mockito.any(CardMemoEntity.class));
    }

    @Test
    public void testUpdateCardMemo() throws Exception {
        CardMemoEntity memo = new CardMemoEntity();
        Mockito.when(cardMemoService.updateMemoCarte(anyLong(), Mockito.any(CardMemoEntity.class))).thenReturn(memo);

        ObjectMapper objectMapper = new ObjectMapper();
        String memoJson = objectMapper.writeValueAsString(memo);

        mockMvc.perform(MockMvcRequestBuilders.put("/memoCarte/modification/1").
                         with(user("testuser").password("password")).
                         contentType(MediaType.APPLICATION_JSON).
                         content(memoJson)).
                 andExpect(status().isOk()).
                 andDo(print());

        Mockito.verify(cardMemoService).updateMemoCarte(anyLong(), Mockito.any(CardMemoEntity.class));
    }

    @Test
    public void testDeleteCardMemoById() throws Exception {
        Mockito.doNothing().when(cardMemoService).deleteMemoCarte(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/memoCarte/suppression/1").
                         with(user("testuser").password("password")).
                         contentType(MediaType.APPLICATION_JSON)).
                 andExpect(status().isNoContent()).
                 andDo(print());

        Mockito.verify(cardMemoService).deleteMemoCarte(anyLong());
    }

}
