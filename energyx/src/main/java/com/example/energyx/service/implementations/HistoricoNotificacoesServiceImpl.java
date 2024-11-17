package com.example.energyx.service.implementations;

import com.example.energyx.dto.HistoricoNotificacoesDTO;
import com.example.energyx.entity.HistoricoNotificacoes;
import com.example.energyx.entity.Notificacoes;
import com.example.energyx.repository.HistoricoNotificacoesRepository;
import com.example.energyx.repository.NotificacoesRepository;
import com.example.energyx.service.interfaces.HistoricoNotificacoesService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricoNotificacoesServiceImpl implements HistoricoNotificacoesService {

    @Autowired
    private HistoricoNotificacoesRepository historicoNotificacoesRepository;

    @Autowired
    private NotificacoesRepository notificacoesRepository;

    @Override
    public HistoricoNotificacoesDTO criarHistoricoNotificacao(HistoricoNotificacoesDTO historicoNotificacoesDTO) {
        HistoricoNotificacoes historicoNotificacoes = mapearDTOParaEntidade(historicoNotificacoesDTO);
        historicoNotificacoes = historicoNotificacoesRepository.save(historicoNotificacoes);
        return mapearEntidadeParaDTO(historicoNotificacoes);
    }

    @Override
    public HistoricoNotificacoesDTO obterHistoricoNotificacaoPorId(Long id) {
        HistoricoNotificacoes historicoNotificacoes = historicoNotificacoesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Histórico de Notificação não encontrado com ID: " + id));
        return mapearEntidadeParaDTO(historicoNotificacoes);
    }

    @Override
    public List<HistoricoNotificacoesDTO> listarHistoricosNotificacoes() {
        List<HistoricoNotificacoes> historicosNotificacoes = historicoNotificacoesRepository.findAll();
        return historicosNotificacoes.stream()
                .map(this::mapearEntidadeParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HistoricoNotificacoesDTO atualizarHistoricoNotificacao(Long id, HistoricoNotificacoesDTO historicoNotificacoesDTO) {
        HistoricoNotificacoes historicoNotificacoes = historicoNotificacoesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Histórico de Notificação não encontrado com ID: " + id));
        mapearDTOParaEntidade(historicoNotificacoesDTO, historicoNotificacoes);
        historicoNotificacoes = historicoNotificacoesRepository.save(historicoNotificacoes);
        return mapearEntidadeParaDTO(historicoNotificacoes);
    }

    @Override
    public boolean excluirHistoricoNotificacao(Long id) {
        historicoNotificacoesRepository.deleteById(id);
        return true;
    }

    // Método privado para converter entidade em DTO
    private HistoricoNotificacoesDTO mapearEntidadeParaDTO(HistoricoNotificacoes historicoNotificacoes) {
        return new HistoricoNotificacoesDTO(
                historicoNotificacoes.getHistNotificacaoId(),
                historicoNotificacoes.getDataHoraAtualizacaoNotif(),
                historicoNotificacoes.getObservacaoNotificacao(),
                historicoNotificacoes.getNotificacoes().getNotificacaoId()
        );
    }

    // Método privado para mapear DTO para a entidade
    private HistoricoNotificacoes mapearDTOParaEntidade(HistoricoNotificacoesDTO historicoNotificacoesDTO) {

        if (historicoNotificacoesDTO.getNotificacaoId() == null) {
            throw new IllegalArgumentException("O ID da Notificação não pode ser nulo.");
        }

        // Busca a entidade Notificacao no banco de dados
        Notificacoes notificacao = notificacoesRepository.findById(historicoNotificacoesDTO.getNotificacaoId())
                .orElseThrow(() -> new EntityNotFoundException("Notificação não encontrada com ID: " + historicoNotificacoesDTO.getNotificacaoId()));

        // Criação do Histórico de Notificação
        HistoricoNotificacoes historicoNotificacoes = new HistoricoNotificacoes();
        historicoNotificacoes.setDataHoraAtualizacaoNotif(historicoNotificacoesDTO.getDataHoraAtualizacaoNotif());
        historicoNotificacoes.setObservacaoNotificacao(historicoNotificacoesDTO.getObservacaoNotificacao());
        historicoNotificacoes.setNotificacoes(notificacao);

        return historicoNotificacoes;
    }

    // Método privado para mapear DTO para a entidade (atualizando um histórico existente)
    private HistoricoNotificacoes mapearDTOParaEntidade(HistoricoNotificacoesDTO historicoNotificacoesDTO, HistoricoNotificacoes historicoNotificacoes) {
        historicoNotificacoes.setDataHoraAtualizacaoNotif(historicoNotificacoesDTO.getDataHoraAtualizacaoNotif());
        historicoNotificacoes.setObservacaoNotificacao(historicoNotificacoesDTO.getObservacaoNotificacao());

        // Busca a entidade Notificacao associada
        Notificacoes notificacao = notificacoesRepository.findById(historicoNotificacoesDTO.getNotificacaoId())
                .orElseThrow(() -> new EntityNotFoundException("Notificação não encontrada com ID: " + historicoNotificacoesDTO.getNotificacaoId()));

        historicoNotificacoes.setNotificacoes(notificacao);

        return historicoNotificacoes;
    }
}
