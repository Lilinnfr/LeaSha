package com.ls.lsback.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ls.lsback.entity.CardMemoEntity;
import com.ls.lsback.entity.ListMemoEntity;
import com.ls.lsback.entity.UtilisateurEntity;
import com.ls.lsback.repository.ListMemoRepository;
import com.ls.lsback.service.ListMemoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListMemoServiceImpl implements ListMemoService {

    private final ListMemoRepository listMemoRepository;

    @Autowired
    public ListMemoServiceImpl(ListMemoRepository listMemoRepository, ObjectMapper objectMapper) {
        this.listMemoRepository = listMemoRepository;
    }

    @Override
    public List<ListMemoEntity> listMemoListe(String email) {
        return listMemoRepository.findByUtilisateurEmail(email);
    }

    @Override
    public ListMemoEntity getMemoListe(long id) {
        return listMemoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mémo non trouvé avec ID " + id));
    }

    @Override
    public ListMemoEntity save(ListMemoEntity listMemoEntity) {
        return listMemoRepository.save(listMemoEntity);
    }

    @Override
    public ListMemoEntity addMemoListe(ListMemoEntity listMemoEntity) {
        UtilisateurEntity utilisateur = (UtilisateurEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        listMemoEntity.setUtilisateur(utilisateur);
        return listMemoRepository.save(listMemoEntity);
    }

    @Override
    public ListMemoEntity updateMemoListe(long id, ListMemoEntity listMemoEntity) {
        ListMemoEntity memoToUpdate = listMemoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pas de mémo avec id " + id));
        memoToUpdate.setTitre(listMemoEntity.getTitre());
        memoToUpdate.setCategorie(listMemoEntity.getCategorie());
        memoToUpdate.setDescription(listMemoEntity.getDescription());
        memoToUpdate.setListe(listMemoEntity.getListe());
        return listMemoRepository.save(memoToUpdate);
    }

    @Override
    public void deleteMemoListe(long id) {
       ListMemoEntity memo = listMemoRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Le mémo avec l'id " + id + " n'existe pas."));
       listMemoRepository.delete(memo);
    }
}
