package com.example.energyx.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoNotificacoesDTO extends RepresentationModel<HistoricoNotificacoesDTO> {

    private Long histNotificacaoId;
    private LocalDateTime dataHoraAtualizacaoNotif;
    private String observacaoNotificacao;
    private Long notificacaoId;  // Referência ao id da entidade Notificacoes

}

