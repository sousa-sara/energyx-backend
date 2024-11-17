package com.example.energyx.repository;

import com.example.energyx.entity.Notificacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacoesRepository extends JpaRepository<Notificacoes, Long> {
}
