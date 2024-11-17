package com.example.energyx.repository;

import com.example.energyx.entity.HistoricoRelatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoRelatorioRepository extends JpaRepository<HistoricoRelatorio, Long> {
}
