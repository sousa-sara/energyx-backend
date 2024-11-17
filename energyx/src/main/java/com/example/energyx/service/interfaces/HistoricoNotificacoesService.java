package com.example.energyx.service.interfaces;

import com.example.energyx.dto.HistoricoNotificacoesDTO;

import java.util.List;

public interface HistoricoNotificacoesService {

    HistoricoNotificacoesDTO criarHistoricoNotificacao(HistoricoNotificacoesDTO historicoNotificacoesDTO);
    HistoricoNotificacoesDTO obterHistoricoNotificacaoPorId(Long id);
    List<HistoricoNotificacoesDTO> listarHistoricosNotificacoes();
    HistoricoNotificacoesDTO atualizarHistoricoNotificacao(Long id, HistoricoNotificacoesDTO historicoNotificacoesDTO);
    boolean excluirHistoricoNotificacao(Long id);

}


