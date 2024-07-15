package com.ls.lsback.service;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import com.ls.lsback.entity.CardEntity;
import com.ls.lsback.entity.CardMemoEntity;
import com.ls.lsback.repository.CardMemoRepository;
import com.ls.lsback.repository.CardRepository;
import com.ls.lsback.service.impl.CardServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardMemoRepository cardMemoRepository;

    @InjectMocks
    private CardServiceImpl cardService;

    private CardMemoEntity memo;
    private CardEntity card;

    @BeforeEach
    void setUp() {
        memo = new CardMemoEntity();
        memo.setId(1L);

        card = new CardEntity();
        card.setId(1L);
        card.setRecto("Recto");
        card.setVerso("Verso");
    }

    @Test
    void testListCartesByMemo() {
        when(cardMemoRepository.findById(1L)).thenReturn(Optional.of(memo));
        when(cardRepository.findAllCardsByMemoId(memo)).thenReturn(Collections.singletonList(card));

        List<CardEntity> result = cardService.listCartesByMemo(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(card, result.get(0));
    }

    @Test
    void testListCartesByMemo_MemoNotFound() {
        when(cardMemoRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            cardService.listCartesByMemo(1L);
        });

        assertEquals("Mémo non trouvé avec ID 1", thrown.getMessage());
    }

    @Test
    void testGetCarte() {
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        CardEntity result = cardService.getCarte(1L);

        assertNotNull(result);
        assertEquals(card, result);
    }

    @Test
    void testGetCarte_NotFound() {
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            cardService.getCarte(1L);
        });

        assertEquals("Carte non trouvée avec ID 1", thrown.getMessage());
    }

    @Test
    void testAddCarte() {
        when(cardMemoRepository.findById(1L)).thenReturn(Optional.of(memo));
        when(cardRepository.save(card)).thenReturn(card);

        CardEntity result = cardService.addCarte(card, 1L);

        assertNotNull(result);
        assertEquals(card, result);
        verify(cardRepository).save(card);
    }

    @Test
    void testAddCarte_MemoNotFound() {
        when(cardMemoRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            cardService.addCarte(card, 1L);
        });

        assertEquals("Mémo non trouvé avec ID 1", thrown.getMessage());
    }

    @Test
    void testUpdateCarte() {
        CardEntity updatedCard = new CardEntity();
        updatedCard.setId(1L);
        updatedCard.setRecto("Updated Recto");
        updatedCard.setVerso("Updated Verso");

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(cardRepository.save(any(CardEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CardEntity result = cardService.updateCarte(1L, updatedCard);

        assertNotNull(result);
        assertEquals(updatedCard.getRecto(), result.getRecto());
        assertEquals(updatedCard.getVerso(), result.getVerso());
        verify(cardRepository).save(any(CardEntity.class));
    }

    @Test
    void testUpdateCarte_NotFound() {
        CardEntity updatedCard = new CardEntity();
        updatedCard.setId(1L);
        updatedCard.setRecto("Updated Recto");
        updatedCard.setVerso("Updated Verso");

        when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            cardService.updateCarte(1L, updatedCard);
        });

        assertEquals("Carte non trouvée avec ID 1", thrown.getMessage());
    }

    @Test
    void testDeleteCarte() {
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        boolean result = cardService.deleteCarte(1L);

        assertTrue(result);
        verify(cardRepository).deleteById(1L);
    }

    @Test
    void testDeleteCarte_NotFound() {
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            cardService.deleteCarte(1L);
        });

        assertEquals("Carte non trouvée avec ID 1", thrown.getMessage());
    }
}