package com.example.energyx.service.interfaces;

import com.example.energyx.dto.HistoricoRelatorioDTO;
import java.util.List;

public interface HistoricoRelatorioService {

    HistoricoRelatorioDTO criarHistoricoRelatorio(HistoricoRelatorioDTO historicoRelatorioDTO);
    HistoricoRelatorioDTO obterHistoricoRelatorioPorId(Long id);
    List<HistoricoRelatorioDTO> listarHistoricoRelatorios(HistoricoRelatorioDTO historicoRelatorioDTO);
    HistoricoRelatorioDTO atualizarHistoricoRelatorio(Long id, HistoricoRelatorioDTO historicoRelatorioDTO);
    boolean excluirHistoricoRelatorio(Long id);

}
