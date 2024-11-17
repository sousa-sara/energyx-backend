package com.example.energyx.service.implementations;

import com.example.energyx.dto.RelatoriosTurnoDTO;
import com.example.energyx.entity.RelatoriosTurno;
import com.example.energyx.entity.Operadores;
import com.example.energyx.entity.Reatores;
import com.example.energyx.repository.RelatoriosTurnoRepository;
import com.example.energyx.repository.OperadoresRepository;
import com.example.energyx.repository.ReatoresRepository;
import com.example.energyx.service.interfaces.RelatoriosTurnoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelatoriosTurnoServiceImpl implements RelatoriosTurnoService {

    @Autowired
    private RelatoriosTurnoRepository relatoriosTurnoRepository;

    @Autowired
    private OperadoresRepository operadoresRepository;

    @Autowired
    private ReatoresRepository reatoresRepository;

    @Override
    public RelatoriosTurnoDTO criarRelatorioTurno(RelatoriosTurnoDTO relatorioTurnoDTO) {
        Operadores operador = operadoresRepository.findById(relatorioTurnoDTO.getOperadorId())
                .orElseThrow(() -> new EntityNotFoundException("Operador não encontrado com ID: " + relatorioTurnoDTO.getOperadorId()));

        Reatores reator = reatoresRepository.findById(relatorioTurnoDTO.getReatorId())
                .orElseThrow(() -> new EntityNotFoundException("Reator não encontrado com ID: " + relatorioTurnoDTO.getReatorId()));

        RelatoriosTurno relatorioTurno = new RelatoriosTurno();
        relatorioTurno.setDataHoraRelatorio(relatorioTurnoDTO.getDataHoraRelatorio());
        relatorioTurno.setResumoAtividades(relatorioTurnoDTO.getResumoAtividades());
        relatorioTurno.setObservacoes(relatorioTurnoDTO.getObservacoes());
        relatorioTurno.setOperador(operador);
        relatorioTurno.setReator(reator);

        relatorioTurno = relatoriosTurnoRepository.save(relatorioTurno);
        return mapearEntidadeParaDTO(relatorioTurno);
    }

    @Override
    public RelatoriosTurnoDTO obterRelatorioTurnoPorId(Long id) {
        RelatoriosTurno relatorioTurno = relatoriosTurnoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relatório de Turno não encontrado com ID: " + id));
        return mapearEntidadeParaDTO(relatorioTurno);
    }

    @Override
    public List<RelatoriosTurnoDTO> listarRelatoriosTurno() {
        List<RelatoriosTurno> relatoriosTurno = relatoriosTurnoRepository.findAll();
        return relatoriosTurno.stream()
                .map(this::mapearEntidadeParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RelatoriosTurnoDTO atualizarRelatorioTurno(Long id, RelatoriosTurnoDTO relatorioTurnoDTO) {
        RelatoriosTurno relatorioTurno = relatoriosTurnoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relatório de Turno não encontrado com ID: " + id));

        Operadores operador = operadoresRepository.findById(relatorioTurnoDTO.getOperadorId())
                .orElseThrow(() -> new EntityNotFoundException("Operador não encontrado com ID: " + relatorioTurnoDTO.getOperadorId()));

        Reatores reator = reatoresRepository.findById(relatorioTurnoDTO.getReatorId())
                .orElseThrow(() -> new EntityNotFoundException("Reator não encontrado com ID: " + relatorioTurnoDTO.getReatorId()));

        relatorioTurno.setDataHoraRelatorio(relatorioTurnoDTO.getDataHoraRelatorio());
        relatorioTurno.setResumoAtividades(relatorioTurnoDTO.getResumoAtividades());
        relatorioTurno.setObservacoes(relatorioTurnoDTO.getObservacoes());
        relatorioTurno.setOperador(operador);
        relatorioTurno.setReator(reator);

        relatorioTurno = relatoriosTurnoRepository.save(relatorioTurno);
        return mapearEntidadeParaDTO(relatorioTurno);
    }

    @Override
    public boolean excluirRelatorioTurno(Long id) {
        relatoriosTurnoRepository.deleteById(id);
        return true;
    }

    // Método privado para mapear entidade para DTO
    private RelatoriosTurnoDTO mapearEntidadeParaDTO(RelatoriosTurno relatorioTurno) {
        return new RelatoriosTurnoDTO(
                relatorioTurno.getRelatorioTurnoId(),
                relatorioTurno.getDataHoraRelatorio(),
                relatorioTurno.getResumoAtividades(),
                relatorioTurno.getObservacoes(),
                relatorioTurno.getOperador().getOperadorId(),
                relatorioTurno.getReator().getReatorId()
        );
    }
}
