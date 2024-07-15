package com.ls.lsback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ls.lsback.entity.UtilisateurEntity;
import com.ls.lsback.security.JwtService;
import com.ls.lsback.service.UtilisateurService;
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

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UtilisateurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtilisateurService utilisateurService;

    @MockBean
    private JwtService jwtService;

    @Test
    @WithMockUser(username = "testuser")
    public void testListUtilisateur() throws Exception {
        List<UtilisateurEntity> utilisateurs = new ArrayList<>();
        UtilisateurEntity utilisateur = new UtilisateurEntity();
        utilisateur.setId(1L);
        utilisateur.setUsername("testuser");
        utilisateur.setEmail("testuser@example.com");
        utilisateurs.add(utilisateur);

        when(utilisateurService.listUtilisateur()).thenReturn(utilisateurs);

        mockMvc.perform(MockMvcRequestBuilders.get("/utilisateur/liste")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("testuser@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("testuser@example.com"))
                .andDo(print());

        Mockito.verify(utilisateurService).listUtilisateur();
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testDeleteUtilisateurById() throws Exception {
        doNothing().when(utilisateurService).deleteUtilisateur(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/utilisateur/suppression/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(print());

        Mockito.verify(utilisateurService).deleteUtilisateur(anyLong());
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testActivation() throws Exception {
        Map<String, String> activation = new HashMap<>();
        activation.put("key", "value");

        mockMvc.perform(MockMvcRequestBuilders.post("/utilisateur/activation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(activation)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        Mockito.verify(utilisateurService).activation(anyMap());
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testDeconnexion() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/utilisateur/deconnexion")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        Mockito.verify(jwtService).deconnexion();
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testNouveauMdp() throws Exception {
        Map<String, String> newPasswordRequest = new HashMap<>();
        newPasswordRequest.put("email", "testuser@example.com");
        newPasswordRequest.put("newPassword", "newPassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/utilisateur/nouveauMdp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newPasswordRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        Mockito.verify(utilisateurService).newPassword(anyMap());
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testRefreshToken() throws Exception {
        Map<String, String> refreshTokenRequest = new HashMap<>();
        refreshTokenRequest.put("refreshToken", "dummyRefreshToken");

        Map<String, String> tokenResponse = new HashMap<>();
        tokenResponse.put("accessToken", "newAccessToken");
        tokenResponse.put("refreshToken", "newRefreshToken");

        when(jwtService.refreshToken(anyMap())).thenReturn(tokenResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/utilisateur/refreshToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(refreshTokenRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value("newAccessToken"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken").value("newRefreshToken"))
                .andDo(print());

        Mockito.verify(jwtService).refreshToken(anyMap());
    }
}