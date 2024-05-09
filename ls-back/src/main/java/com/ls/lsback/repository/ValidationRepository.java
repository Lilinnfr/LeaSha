package com.ls.lsback.repository;

import com.ls.lsback.entity.ValidationEntity;
import org.apache.el.util.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidationRepository extends JpaRepository<ValidationEntity, Long> {
    Optional<ValidationEntity> findByCode(String code);
}
