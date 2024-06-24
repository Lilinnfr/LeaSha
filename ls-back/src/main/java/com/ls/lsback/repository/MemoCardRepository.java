package com.ls.lsback.repository;

import com.ls.lsback.entity.MemoCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoCardRepository extends JpaRepository<MemoCardEntity, Long> {
    List<MemoCardEntity> findByUtilisateurEmail(String email);
}
