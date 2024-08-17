package com.ls.lsback.service;

import com.ls.lsback.entity.CardMemoEntity;
import com.ls.lsback.entity.UtilisateurEntity;
import com.ls.lsback.model.CategorieMemo;
import com.ls.lsback.repository.CardMemoRepository;
import com.ls.lsback.service.impl.CardMemoServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CardMemoServiceTest {

    @Mock
    private CardMemoRepository cardMemoRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private CardMemoServiceImpl cardMemoService;

    private CardMemoEntity cardMemo;
    private UtilisateurEntity utilisateur;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        utilisateur = new UtilisateurEntity();
        utilisateur.setId(1L);
        utilisateur.setEmail("testuser@example.com");

        cardMemo = new CardMemoEntity();
        cardMemo.setId(1L);
        cardMemo.setTitre("Test Memo");
        cardMemo.setDescription("Test Description");
        cardMemo.setDateCreation(new Timestamp(System.currentTimeMillis()));
        cardMemo.setDateModif(new Timestamp(System.currentTimeMillis()));
        cardMemo.setCategorie(CategorieMemo.ANGLAIS);
        cardMemo.setUtilisateur(utilisateur);

        when(authentication.getPrincipal()).thenReturn(utilisateur);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testListMemoCarte() {
        when(cardMemoRepository.findByUtilisateurEmail(anyString())).thenReturn(List.of(cardMemo));

        List<CardMemoEntity> memos = cardMemoService.listMemoCarte("testuser@example.com");

        assertNotNull(memos);
        assertEquals(1, memos.size());
        assertEquals(cardMemo, memos.get(0));
    }

    @Test
    void testGetMemoCarte() {
        when(cardMemoRepository.findById(anyLong())).thenReturn(Optional.of(cardMemo));

        CardMemoEntity foundMemo = cardMemoService.getMemoCarte(1L);

        assertNotNull(foundMemo);
        assertEquals(cardMemo, foundMemo);
        assertEquals(cardMemo.getDateCreation(), foundMemo.getDateCreation());
        assertEquals(cardMemo.getDateModif(), foundMemo.getDateModif());
    }

    @Test
    void testGetMemoCarte_NotFound() {
        when(cardMemoRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            cardMemoService.getMemoCarte(1L);
        });

        assertEquals("Mémo non trouvé avec ID 1", exception.getMessage());
    }

    @Test
    void testAddMemoCarte() {
        when(cardMemoRepository.save(any(CardMemoEntity.class))).thenReturn(cardMemo);

        CardMemoEntity addedMemo = cardMemoService.addMemoCarte(cardMemo);

        assertNotNull(addedMemo);
        assertEquals(cardMemo, addedMemo);
        assertEquals(utilisateur, addedMemo.getUtilisateur());
        verify(cardMemoRepository).save(cardMemo);
    }

    @Test
    void testUpdateMemoCarte_NotFound() {
        when(cardMemoRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            cardMemoService.updateMemoCarte(1L, cardMemo);
        });

        assertEquals("Pas de mémo avec id 1", exception.getMessage());
    }

    @Test
    void testDeleteMemoCarte() {
        when(cardMemoRepository.findById(anyLong())).thenReturn(Optional.of(cardMemo));

        cardMemoService.deleteMemoCarte(1L);

        verify(cardMemoRepository).delete(cardMemo);
    }

    @Test
    void testDeleteMemoCarte_NotFound() {
        when(cardMemoRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            cardMemoService.deleteMemoCarte(1L);
        });

        assertEquals("Le mémo avec l'id 1 n'existe pas.", exception.getMessage());
    }
}