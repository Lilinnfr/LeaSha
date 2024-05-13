package com.ls.lsback.repository;

import com.ls.lsback.entity.JwtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.stream.Stream;

public interface JwtRepository extends JpaRepository<JwtEntity, Long> {
    Optional<JwtEntity> findByValeurAndDesactiveAndExpire(String valeur, boolean desactive, boolean expire);
    @Query("FROM JwtEntity j WHERE j.expire = :expire AND j.desactive = :desactive AND j.utilisateur.email = :email")
    Optional<JwtEntity> findUserValidToken(String email, boolean desactive, boolean expire);

    @Query("FROM JwtEntity j WHERE j.utilisateur.email = :email")
    Stream<JwtEntity> findUser(String email);

    void deleteAllByExpireAndDesactive(boolean expire, boolean desactive);
}
