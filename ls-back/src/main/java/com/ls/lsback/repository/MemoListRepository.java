package com.ls.lsback.repository;

import com.ls.lsback.entity.MemoListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoListRepository extends JpaRepository<MemoListEntity, Long> {
}
