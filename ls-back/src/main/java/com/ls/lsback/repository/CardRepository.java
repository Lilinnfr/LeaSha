package com.ls.lsback.repository;

import com.ls.lsback.entity.CardEntity;
import com.ls.lsback.entity.CardMemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    List<CardEntity> findAllCardsByMemoId(CardMemoEntity cardMemoEntity);

    Optional<CardEntity> findById(long id);

}
