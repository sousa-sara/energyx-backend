package com.example.energyx.service.implementations;

import com.example.energyx.dto.HistoricoRelatorioDTO;
import com.example.energyx.entity.HistoricoRelatorio;
import com.example.energyx.entity.RelatoriosTurno;
import com.example.energyx.repository.HistoricoRelatorioRepository;
import com.example.energyx.repository.RelatoriosTurnoRepository;
import com.example.energyx.service.interfaces.HistoricoRelatorioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricoRelatorioServiceImpl implements HistoricoRelatorioService {

    @Autowired
    private HistoricoRelatorioRepository historicoRelatorioRepository;

    @Autowired
    private RelatoriosTurnoRepository relatoriosTurnoRepository;

    @Override
    public HistoricoRelatorioDTO criarHistoricoRelatorio(HistoricoRelatorioDTO historicoRelatorioDTO) {
        HistoricoRelatorio historicoRelatorio = mapearDTOParaEntidade(historicoRelatorioDTO);
        historicoRelatorio = historicoRelatorioRepository.save(historicoRelatorio);
        return mapearEntidadeParaDTO(historicoRelatorio);
    }

    @Override
    public HistoricoRelatorioDTO obterHistoricoRelatorioPorId(Long id) {
        HistoricoRelatorio historicoRelatorio = historicoRelatorioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Histórico de Relatório não encontrado com ID: " + id));
        return mapearEntidadeParaDTO(historicoRelatorio);
    }

    @Override
    public List<HistoricoRelatorioDTO> listarHistoricoRelatorios(HistoricoRelatorioDTO historicoRelatorioDTO) {
        List<HistoricoRelatorio> historicosRelatorio = historicoRelatorioRepository.findAll();
        return historicosRelatorio.stream()
                .map(this::mapearEntidadeParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HistoricoRelatorioDTO atualizarHistoricoRelatorio(Long id, HistoricoRelatorioDTO historicoRelatorioDTO) {
        HistoricoRelatorio historicoRelatorio = historicoRelatorioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Histórico de Relatório não encontrado com ID: " + id));
        mapearDTOParaEntidade(historicoRelatorioDTO, historicoRelatorio);
        historicoRelatorio = historicoRelatorioRepository.save(historicoRelatorio);
        return mapearEntidadeParaDTO(historicoRelatorio);
    }

    @Override
    public boolean excluirHistoricoRelatorio(Long id) {
        historicoRelatorioRepository.deleteById(id);
        return true;
    }

    // Método privado para converter entidade em DTO
    private HistoricoRelatorioDTO mapearEntidadeParaDTO(HistoricoRelatorio historicoRelatorio) {
        return new HistoricoRelatorioDTO(
                historicoRelatorio.getHistRelatorioId(),
                historicoRelatorio.getDataHoraAtualizacao(),
                historicoRelatorio.getObservacoes(),
                historicoRelatorio.getRelatorioTurno().getRelatorioTurnoId()
        );
    }

    // Método privado para mapear DTO para a entidade
    private HistoricoRelatorio mapearDTOParaEntidade(HistoricoRelatorioDTO historicoRelatorioDTO) {

        if (historicoRelatorioDTO.getRelatorioTurnoId() == null) {
            throw new IllegalArgumentException("O ID do Relatório de Turno não pode ser nulo.");
        }

        // Busca a entidade RelatorioTurno no banco de dados
        RelatoriosTurno relatoriosTurno = relatoriosTurnoRepository.findById(historicoRelatorioDTO.getRelatorioTurnoId())
                .orElseThrow(() -> new EntityNotFoundException("Relatório de Turno não encontrado com ID: " + historicoRelatorioDTO.getRelatorioTurnoId()));

        // Criação do Histórico de Relatório
        HistoricoRelatorio historicoRelatorio = new HistoricoRelatorio();
        historicoRelatorio.setDataHoraAtualizacao(historicoRelatorioDTO.getDataHoraAtualizacao());
        historicoRelatorio.setObservacoes(historicoRelatorioDTO.getObservacoes());
        historicoRelatorio.setRelatorioTurno(relatoriosTurno);

        return historicoRelatorio;
    }

    // Método privado para mapear DTO para a entidade (atualizando um histórico de relatório existente)
    private HistoricoRelatorio mapearDTOParaEntidade(HistoricoRelatorioDTO historicoRelatorioDTO, HistoricoRelatorio historicoRelatorio) {
        historicoRelatorio.setDataHoraAtualizacao(historicoRelatorioDTO.getDataHoraAtualizacao());
        historicoRelatorio.setObservacoes(historicoRelatorioDTO.getObservacoes());

        // Busca a entidade RelatorioTurno associada
        RelatoriosTurno relatoriosTurno = relatoriosTurnoRepository.findById(historicoRelatorioDTO.getRelatorioTurnoId())
                .orElseThrow(() -> new EntityNotFoundException("Relatório de Turno não encontrado com ID: " + historicoRelatorioDTO.getRelatorioTurnoId()));

        historicoRelatorio.setRelatorioTurno(relatoriosTurno);

        return historicoRelatorio;
    }
}
