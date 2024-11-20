package com.example.energyx.repository;

import com.example.energyx.entity.Operadores;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperadoresRepository extends JpaRepository<Operadores, Long> {

    Page<Operadores> findAll(Pageable pageable);
    Optional<Operadores> findByLorAndSenhaOperador(String lor, String senhaOperador);
}
