package com.ls.lsback.repository;

import com.ls.lsback.entity.MemoCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoCardRepository extends JpaRepository<MemoCardEntity, Long> {
}
