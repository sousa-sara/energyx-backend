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
public class NotificacoesDTO extends RepresentationModel<NotificacoesDTO> {

    private Long notificacaoId;
    private LocalDateTime dataHoraNotificacao;
    private Long statusNotificacaoId;  // Referência ao id de StatusNotificacao
    private Long operadorId;  // Referência ao id de Operadores
    private Long reatorId;  // Referência ao id de Reatores

}