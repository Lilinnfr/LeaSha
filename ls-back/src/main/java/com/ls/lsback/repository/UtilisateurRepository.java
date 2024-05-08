package com.ls.lsback.repository;

import com.ls.lsback.entity.UtilisateurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<UtilisateurEntity, Long> {
    Optional<UtilisateurEntity> findByEmail(String email);
}
