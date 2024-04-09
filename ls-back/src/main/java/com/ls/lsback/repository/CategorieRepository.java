package com.ls.lsback.repository;

import com.ls.lsback.entity.CategorieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends JpaRepository<CategorieEntity, Long> {
}
