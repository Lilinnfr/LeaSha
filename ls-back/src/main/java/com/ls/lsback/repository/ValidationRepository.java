package com.ls.lsback.repository;

import com.ls.lsback.entity.ValidationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidationRepository extends JpaRepository<ValidationEntity, Long> {
}
