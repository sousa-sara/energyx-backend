package com.example.energyx.service.interfaces;

import com.example.energyx.dto.TurnosDTO;
import java.util.List;

public interface TurnosService {

    TurnosDTO criarTurno(TurnosDTO turnosDTO);
    TurnosDTO obterTurnoPorId(Long id);
    List<TurnosDTO> listarTurnos(TurnosDTO turnosDTO);
    TurnosDTO atualizarTurno(Long id, TurnosDTO turnosDTO);
    boolean excluirTurno(Long id);

}
