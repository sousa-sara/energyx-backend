package com.example.energyx.service.implementations;

import com.example.energyx.dto.NotificacoesDTO;
import com.example.energyx.entity.Notificacoes;
import com.example.energyx.entity.Operadores;
import com.example.energyx.entity.Reatores;
import com.example.energyx.entity.StatusNotificacao;
import com.example.energyx.repository.NotificacoesRepository;
import com.example.energyx.repository.OperadoresRepository;
import com.example.energyx.repository.ReatoresRepository;
import com.example.energyx.repository.StatusNotificacaoRepository;
import com.example.energyx.service.interfaces.NotificacoesService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacoesServiceImpl implements NotificacoesService {

    @Autowired
    private NotificacoesRepository notificacoesRepository;

    @Autowired
    private StatusNotificacaoRepository statusNotificacaoRepository;

    @Autowired
    private OperadoresRepository operadoresRepository;

    @Autowired
    private ReatoresRepository reatoresRepository;

    @Override
    public NotificacoesDTO criarNotificacao(NotificacoesDTO notificacoesDTO) {
        Notificacoes notificacoes = mapearDTOParaEntidade(notificacoesDTO);
        notificacoes = notificacoesRepository.save(notificacoes);
        return mapearEntidadeParaDTO(notificacoes);
    }

    @Override
    public NotificacoesDTO obterNotificacaoPorId(Long id) {
        Notificacoes notificacoes = notificacoesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notificação não encontrada com ID: " + id));
        return mapearEntidadeParaDTO(notificacoes);
    }

    @Override
    public List<NotificacoesDTO> listarNotificacoes(NotificacoesDTO notificacoesDTO) {
        List<Notificacoes> notificacoesList = notificacoesRepository.findAll();
        return notificacoesList.stream()
                .map(this::mapearEntidadeParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NotificacoesDTO atualizarNotificacao(Long id, NotificacoesDTO notificacoesDTO) {
        Notificacoes notificacoes = notificacoesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notificação não encontrada com ID: " + id));
        notificacoes = mapearDTOParaEntidade(notificacoesDTO, notificacoes);
        notificacoes = notificacoesRepository.save(notificacoes);
        return mapearEntidadeParaDTO(notificacoes);
    }

    @Override
    public boolean excluirNotificacao(Long id) {
        notificacoesRepository.deleteById(id);
        return true;
    }

    // Método privado para mapear entidade para DTO
    private NotificacoesDTO mapearEntidadeParaDTO(Notificacoes notificacoes) {
        return new NotificacoesDTO(
                notificacoes.getNotificacaoId(),
                notificacoes.getDataHoraNotificacao(),
                notificacoes.getStatusNotificacao().getStatusNotificacaoId(),
                notificacoes.getOperadores().getOperadorId(),
                notificacoes.getReatores().getReatorId()
        );
    }

    // Método privado para mapear DTO para entidade
    private Notificacoes mapearDTOParaEntidade(NotificacoesDTO notificacoesDTO) {
        Notificacoes notificacoes = new Notificacoes();
        StatusNotificacao statusNotificacao = statusNotificacaoRepository.findById(notificacoesDTO.getStatusNotificacaoId())
                .orElseThrow(() -> new EntityNotFoundException("StatusNotificacao não encontrado"));
        Operadores operador = operadoresRepository.findById(notificacoesDTO.getOperadorId())
                .orElseThrow(() -> new EntityNotFoundException("Operador não encontrado"));
        Reatores reator = reatoresRepository.findById(notificacoesDTO.getReatorId())
                .orElseThrow(() -> new EntityNotFoundException("Reator não encontrado"));

        notificacoes.setStatusNotificacao(statusNotificacao);
        notificacoes.setOperadores(operador);
        notificacoes.setReatores(reator);
        notificacoes.setDataHoraNotificacao(notificacoesDTO.getDataHoraNotificacao());

        return notificacoes;
    }

    // Método privado para mapear DTO para entidade (atualizando uma notificação existente)
    private Notificacoes mapearDTOParaEntidade(NotificacoesDTO notificacoesDTO, Notificacoes notificacoes) {
        StatusNotificacao statusNotificacao = statusNotificacaoRepository.findById(notificacoesDTO.getStatusNotificacaoId())
                .orElseThrow(() -> new EntityNotFoundException("StatusNotificacao não encontrado"));
        Operadores operador = operadoresRepository.findById(notificacoesDTO.getOperadorId())
                .orElseThrow(() -> new EntityNotFoundException("Operador não encontrado"));
        Reatores reator = reatoresRepository.findById(notificacoesDTO.getReatorId())
                .orElseThrow(() -> new EntityNotFoundException("Reator não encontrado"));

        notificacoes.setStatusNotificacao(statusNotificacao);
        notificacoes.setOperadores(operador);
        notificacoes.setReatores(reator);
        notificacoes.setDataHoraNotificacao(notificacoesDTO.getDataHoraNotificacao());

        return notificacoes;
    }
}
