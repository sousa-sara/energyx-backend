package com.example.energyx.repository;

import com.example.energyx.entity.StatusNotificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusNotificacaoRepository extends JpaRepository<StatusNotificacao, Long> {
}
