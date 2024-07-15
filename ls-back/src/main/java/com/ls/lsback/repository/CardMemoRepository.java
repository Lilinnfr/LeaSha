package com.ls.lsback.repository;

import com.ls.lsback.entity.CardMemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardMemoRepository extends JpaRepository<CardMemoEntity, Long> {
    List<CardMemoEntity> findByUtilisateurEmail(String email);
}
