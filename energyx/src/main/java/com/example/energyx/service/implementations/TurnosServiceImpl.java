package com.example.energyx.service.implementations;

import com.example.energyx.dto.TurnosDTO;
import com.example.energyx.entity.Turnos;
import com.example.energyx.repository.TurnosRepository;
import com.example.energyx.service.interfaces.TurnosService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurnosServiceImpl implements TurnosService {

    @Autowired
    private TurnosRepository turnosRepository;

    @Override
    public TurnosDTO criarTurno(TurnosDTO turnosDTO) {
        Turnos turno = mapearDTOParaEntidade(turnosDTO);
        turno = turnosRepository.save(turno);
        return mapearEntidadeParaDTO(turno);
    }

    @Override
    public TurnosDTO obterTurnoPorId(Long id) {
        Turnos turno = turnosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Turno não encontrado com ID: " + id));
        return mapearEntidadeParaDTO(turno);
    }

    @Override
    public List<TurnosDTO> listarTurnos(TurnosDTO turnosDTO) {
        List<Turnos> turnos = turnosRepository.findAll();
        return turnos.stream()
                .map(this::mapearEntidadeParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TurnosDTO atualizarTurno(Long id, TurnosDTO turnosDTO) {
        Turnos turno = turnosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Turno não encontrado com ID: " + id));
        turno = mapearDTOParaEntidade(turnosDTO, turno);
        turno = turnosRepository.save(turno);
        return mapearEntidadeParaDTO(turno);
    }

    @Override
    public boolean excluirTurno(Long id) {
        turnosRepository.deleteById(id);
        return true;
    }

    // Método privado para mapear entidade para DTO
    private TurnosDTO mapearEntidadeParaDTO(Turnos turno) {
        return new TurnosDTO(
                turno.getTurnoId(),
                turno.getDescricaoTurno()
        );
    }

    // Método privado para mapear DTO para entidade
    private Turnos mapearDTOParaEntidade(TurnosDTO turnosDTO) {
        Turnos turno = new Turnos();
        turno.setDescricaoTurno(turnosDTO.getDescricaoTurno());
        return turno;
    }

    // Método privado para mapear DTO para entidade (atualizando um turno existente)
    private Turnos mapearDTOParaEntidade(TurnosDTO turnosDTO, Turnos turno) {
        turno.setDescricaoTurno(turnosDTO.getDescricaoTurno());
        return turno;
    }
}
