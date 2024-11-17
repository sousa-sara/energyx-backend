package com.example.energyx.repository;

import com.example.energyx.entity.Operadores;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperadoresRepository extends JpaRepository<Operadores, Long> {

    Page<Operadores> findAll(Pageable pageable);

}
