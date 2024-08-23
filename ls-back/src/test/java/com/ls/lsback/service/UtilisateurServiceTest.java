package com.ls.lsback.service;

import com.ls.lsback.entity.UtilisateurEntity;
import com.ls.lsback.entity.ValidationEntity;
import com.ls.lsback.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private UtilisateurService utilisateurService;

    private UtilisateurEntity utilisateur;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        utilisateur = new UtilisateurEntity();
        utilisateur.setId(1L);
        utilisateur.setEmail("testuser@example.com");
        utilisateur.setPassword("password");
    }

    @Test
    void testListUtilisateur() {
        when(utilisateurRepository.findAll()).thenReturn(List.of(utilisateur));
        List<UtilisateurEntity> utilisateurs = utilisateurService.listUtilisateur();
        assertNotNull(utilisateurs);
        assertEquals(1, utilisateurs.size());
        assertEquals(utilisateur, utilisateurs.get(0));
    }

    @Test
    void testAddUtilisateur() {
        when(utilisateurRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");
        when(utilisateurRepository.save(any(UtilisateurEntity.class))).thenReturn(utilisateur);

        utilisateur.setPassword("password");
        UtilisateurEntity savedUtilisateur = utilisateurService.addUtilisateur(utilisateur);

        assertNotNull(savedUtilisateur);
        assertEquals("encryptedPassword", savedUtilisateur.getPassword());
        verify(validationService).saveUser(any(UtilisateurEntity.class));
    }

    @Test
    void testAddUtilisateur_EmailInvalide() {
        utilisateur.setEmail("invalid-email");
        Exception exception = assertThrows(RuntimeException.class, () -> {
            utilisateurService.addUtilisateur(utilisateur);
        });
        assertEquals("Le format de l'email est invalide", exception.getMessage());
    }

    @Test
    void testActivation() {
        ValidationEntity validation = new ValidationEntity();
        validation.setExpiration(Instant.now().plusSeconds(3600));
        validation.setUtilisateur(utilisateur);

        when(validationService.getValidationByCode(anyString())).thenReturn(validation);
        when(utilisateurRepository.findById(anyLong())).thenReturn(Optional.of(utilisateur));

        utilisateurService.activation(Map.of("code", "validCode"));

        assertTrue(utilisateur.isActif());
        verify(utilisateurRepository).save(any(UtilisateurEntity.class));
    }

    @Test
    void testActivation_CodeExpiré() {
        ValidationEntity validation = new ValidationEntity();
        validation.setExpiration(Instant.now().minusSeconds(3600));
        validation.setUtilisateur(utilisateur);

        when(validationService.getValidationByCode(anyString())).thenReturn(validation);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            utilisateurService.activation(Map.of("code", "expiredCode"));
        });

        assertEquals("Votre code a expiré", exception.getMessage());
    }

    @Test
    void testDeleteUtilisateur() {
        when(utilisateurRepository.findById(anyLong())).thenReturn(Optional.of(utilisateur));
        utilisateurService.deleteUtilisateur(1L);
        verify(utilisateurRepository).delete(any(UtilisateurEntity.class));
    }

    @Test
    void testDeleteUtilisateur_UtilisateurInexistant() {
        when(utilisateurRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            utilisateurService.deleteUtilisateur(1L);
        });
        assertEquals("L'utilisateur avec l'ID 1 n'existe pas.", exception.getMessage());
    }

    @Test
    void testLoadUserByUsername() {
        when(utilisateurRepository.findByEmail(anyString())).thenReturn(Optional.of(utilisateur));
        UtilisateurEntity foundUtilisateur = utilisateurService.loadUserByUsername("testuser@example.com");
        assertNotNull(foundUtilisateur);
        assertEquals(utilisateur, foundUtilisateur);
    }

    @Test
    void testLoadUserByUsername_UtilisateurNonTrouvé() {
        when(utilisateurRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            utilisateurService.loadUserByUsername("nonexistentuser@example.com");
        });
        assertEquals("Aucun utilisateur ne correspond à cet email", exception.getMessage());
    }

    @Test
    void testChangePassword() {
        when(utilisateurRepository.findByEmail(anyString())).thenReturn(Optional.of(utilisateur));

        Map<String, String> params = Map.of("email", "testuser@example.com", "currentPassword", "oldPassword", "newPassword", "newPassword");

        doNothing().when(validationService).saveUser(any(UtilisateurEntity.class));

        utilisateurService.changePassword(params);

        verify(validationService).saveUser(any(UtilisateurEntity.class));
    }

    @Test
    void testNewPassword() {
        ValidationEntity validation = new ValidationEntity();
        validation.setExpiration(Instant.now().plusSeconds(3600));
        validation.setUtilisateur(utilisateur);

        when(validationService.getValidationByCode(anyString())).thenReturn(validation);
        when(utilisateurRepository.findByEmail(anyString())).thenReturn(Optional.of(utilisateur));
        when(passwordEncoder.encode(anyString())).thenReturn("newEncryptedPassword");

        Map<String, String> params = Map.of("email", "testuser@example.com", "code", "validCode", "password", "newPassword");

        utilisateurService.newPassword(params);

        verify(utilisateurRepository).save(any(UtilisateurEntity.class));
        assertEquals("newEncryptedPassword", utilisateur.getPassword());
    }
}
