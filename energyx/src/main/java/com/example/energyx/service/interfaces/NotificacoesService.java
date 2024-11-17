package com.example.energyx.service.interfaces;

import com.example.energyx.dto.NotificacoesDTO;
import java.util.List;

public interface NotificacoesService {

    NotificacoesDTO criarNotificacao(NotificacoesDTO notificacoesDTO);
    NotificacoesDTO obterNotificacaoPorId(Long id);
    List<NotificacoesDTO> listarNotificacoes(NotificacoesDTO notificacoesDTO);
    NotificacoesDTO atualizarNotificacao(Long id, NotificacoesDTO notificacoesDTO);
    boolean excluirNotificacao(Long id);

}
