package com.example.energyx.service.interfaces;

import com.example.energyx.dto.RelatoriosTurnoDTO;
import java.util.List;

public interface RelatoriosTurnoService {

    RelatoriosTurnoDTO criarRelatorioTurno(RelatoriosTurnoDTO relatorioTurnoDTO);
    RelatoriosTurnoDTO obterRelatorioTurnoPorId(Long id);
    List<RelatoriosTurnoDTO> listarRelatoriosTurno();
    RelatoriosTurnoDTO atualizarRelatorioTurno(Long id, RelatoriosTurnoDTO relatorioTurnoDTO);
    boolean excluirRelatorioTurno(Long id);

}
