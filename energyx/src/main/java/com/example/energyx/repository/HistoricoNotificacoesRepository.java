package com.example.energyx.repository;

import com.example.energyx.entity.HistoricoNotificacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoNotificacoesRepository extends JpaRepository<HistoricoNotificacoes, Long> {
}

