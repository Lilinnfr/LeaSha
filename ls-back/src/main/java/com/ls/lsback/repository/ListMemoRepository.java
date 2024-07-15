package com.ls.lsback.repository;

import com.ls.lsback.entity.ListMemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListMemoRepository extends JpaRepository<ListMemoEntity, Long> {
}
