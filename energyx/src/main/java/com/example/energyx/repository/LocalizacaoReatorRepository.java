package com.example.energyx.repository;

import com.example.energyx.entity.LocalizacaoReator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizacaoReatorRepository extends JpaRepository<LocalizacaoReator, Long> {
}
