package com.example.energyx.service.implementations;

import com.example.energyx.dto.OperadoresDTO;
import com.example.energyx.entity.Operadores;
import com.example.energyx.entity.Turnos;
import com.example.energyx.repository.OperadoresRepository;
import com.example.energyx.repository.TurnosRepository;
import com.example.energyx.service.interfaces.OperadoresService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperadoresServiceImpl implements OperadoresService {

    @Autowired
    private OperadoresRepository operadoresRepository;

    @Autowired
    private TurnosRepository turnosRepository;

    @Override
    public OperadoresDTO cadastrarOperador(OperadoresDTO operadoresDTO) {
        operadoresDTO.setSenhaOperador(operadoresDTO.getSenhaOperador());
        Operadores operador = mapearDTOParaEntidade(operadoresDTO);
        operador = operadoresRepository.save(operador);
        return mapearEntidadeParaDTO(operador);
    }

    @Override
    public OperadoresDTO obterOperadorPorId(Long id) {
        Operadores operador = operadoresRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operador não encontrado com ID: " + id));
        return mapearEntidadeParaDTO(operador);
    }

    @Override
    public Page<OperadoresDTO> listarOperadoresPaginado(int pagina, int tamanho) {
        // Adiciona verificação para garantir que os parâmetros de página e tamanho são válidos
        if (pagina < 0 || tamanho <= 0) {
            throw new IllegalArgumentException("Parâmetros de página ou tamanho inválidos");
        }

        // Cria um objeto Pageable com os parâmetros de página e tamanho
        Pageable pageable = PageRequest.of(pagina, tamanho);

        // Busca os operadores no repositório de forma paginada
        Page<Operadores> operadoresPage = operadoresRepository.findAll(pageable);

        // Caso não encontre resultados, retorna uma página vazia
        if (operadoresPage.isEmpty()) {
            return Page.empty();
        }

        // Mapeia a página de Operadores para uma página de OperadoresDTO
        return operadoresPage.map(this::mapearEntidadeParaDTO);
    }

    @Override
    public OperadoresDTO atualizarOperador(Long id, OperadoresDTO operadoresDTO) {
        Operadores operador = operadoresRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operador não encontrado com ID: " + id));
        operador = mapearDTOParaEntidade(operadoresDTO, operador);
        operador = operadoresRepository.save(operador);
        return mapearEntidadeParaDTO(operador);
    }

    @Override
    public boolean excluirOperador(Long id) {
        operadoresRepository.deleteById(id);
        return true;
    }

    // Método privado para mapear entidade para DTO
    private OperadoresDTO mapearEntidadeParaDTO(Operadores operador) {
        return new OperadoresDTO(
                operador.getOperadorId(),
                operador.getNomeOperador(),
                operador.getSenhaOperador(),
                operador.getCargo(),
                operador.getTurnos().getTurnoId(),
                operador.getLor()
        );
    }

    // Método privado para mapear DTO para entidade
    private Operadores mapearDTOParaEntidade(OperadoresDTO operadoresDTO) {
        Turnos turno = turnosRepository.findById(operadoresDTO.getTurnoId())
                .orElseThrow(() -> new EntityNotFoundException("Turno não encontrado com ID: " + operadoresDTO.getTurnoId()));

        Operadores operador = new Operadores();
        operador.setNomeOperador(operadoresDTO.getNomeOperador());
        operador.setSenhaOperador(operadoresDTO.getSenhaOperador());
        operador.setCargo(operadoresDTO.getCargo());
        operador.setTurnos(turno);
        operador.setLor(operadoresDTO.getLor());
        return operador;
    }

    // Método privado para mapear DTO para entidade (atualizando um operador existente)
    private Operadores mapearDTOParaEntidade(OperadoresDTO operadoresDTO, Operadores operador) {
        Turnos turno = turnosRepository.findById(operadoresDTO.getTurnoId())
                .orElseThrow(() -> new EntityNotFoundException("Turno não encontrado com ID: " + operadoresDTO.getTurnoId()));

        operador.setNomeOperador(operadoresDTO.getNomeOperador());
        operador.setSenhaOperador(operadoresDTO.getSenhaOperador());
        operador.setCargo(operadoresDTO.getCargo());
        operador.setTurnos(turno);
        operador.setLor(operadoresDTO.getLor());
        return operador;
    }
}
