package com.example.energyx.service.implementations;

import com.example.energyx.dto.StatusNotificacaoDTO;
import com.example.energyx.entity.StatusNotificacao;
import com.example.energyx.repository.StatusNotificacaoRepository;
import com.example.energyx.service.interfaces.StatusNotificacaoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusNotificacaoServiceImpl implements StatusNotificacaoService {

    @Autowired
    private StatusNotificacaoRepository statusNotificacaoRepository;

    @Override
    public StatusNotificacaoDTO criarStatusNotificacao(StatusNotificacaoDTO statusNotificacaoDTO) {
        StatusNotificacao statusNotificacao = mapearDTOParaEntidade(statusNotificacaoDTO);
        statusNotificacao = statusNotificacaoRepository.save(statusNotificacao);
        return mapearEntidadeParaDTO(statusNotificacao);
    }

    @Override
    public StatusNotificacaoDTO obterStatusNotificacaoPorId(Long id) {
        StatusNotificacao statusNotificacao = statusNotificacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status Notificação não encontrada com ID: " + id));
        return mapearEntidadeParaDTO(statusNotificacao);
    }

    @Override
    public List<StatusNotificacaoDTO> listarStatusNotificacao(StatusNotificacaoDTO statusNotificacaoDTO) {
        List<StatusNotificacao> statusNotificacoes = statusNotificacaoRepository.findAll();
        return statusNotificacoes.stream()
                .map(this::mapearEntidadeParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StatusNotificacaoDTO atualizarStatusNotificacao(Long id, StatusNotificacaoDTO statusNotificacaoDTO) {
        StatusNotificacao statusNotificacao = statusNotificacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status Notificação não encontrada com ID: " + id));
        statusNotificacao = mapearDTOParaEntidade(statusNotificacaoDTO, statusNotificacao);
        statusNotificacao = statusNotificacaoRepository.save(statusNotificacao);
        return mapearEntidadeParaDTO(statusNotificacao);
    }

    @Override
    public boolean excluirStatusNotificacao(Long id) {
        statusNotificacaoRepository.deleteById(id);
        return true;
    }

    // Método privado para mapear entidade para DTO
    private StatusNotificacaoDTO mapearEntidadeParaDTO(StatusNotificacao statusNotificacao) {
        return new StatusNotificacaoDTO(
                statusNotificacao.getStatusNotificacaoId(),
                statusNotificacao.getDescrStatus()
        );
    }

    // Método privado para mapear DTO para entidade
    private StatusNotificacao mapearDTOParaEntidade(StatusNotificacaoDTO statusNotificacaoDTO) {
        StatusNotificacao statusNotificacao = new StatusNotificacao();
        statusNotificacao.setDescrStatus(statusNotificacaoDTO.getDescrStatus());
        return statusNotificacao;
    }

    // Método privado para mapear DTO para entidade (atualizando um status existente)
    private StatusNotificacao mapearDTOParaEntidade(StatusNotificacaoDTO statusNotificacaoDTO, StatusNotificacao statusNotificacao) {
        statusNotificacao.setDescrStatus(statusNotificacaoDTO.getDescrStatus());
        return statusNotificacao;
    }
}
