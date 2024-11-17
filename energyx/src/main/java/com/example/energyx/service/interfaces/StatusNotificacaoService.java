package com.example.energyx.service.interfaces;

import com.example.energyx.dto.StatusNotificacaoDTO;
import java.util.List;

public interface StatusNotificacaoService {

    StatusNotificacaoDTO criarStatusNotificacao(StatusNotificacaoDTO statusNotificacaoDTO);
    StatusNotificacaoDTO obterStatusNotificacaoPorId(Long id);
    List<StatusNotificacaoDTO> listarStatusNotificacao(StatusNotificacaoDTO statusNotificacaoDTO);
    StatusNotificacaoDTO atualizarStatusNotificacao(Long id, StatusNotificacaoDTO statusNotificacaoDTO);
    boolean excluirStatusNotificacao(Long id);

}
