package com.example.energyx.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoRelatorioDTO extends RepresentationModel<HistoricoRelatorioDTO> {

    private Long histRelatorioId;
    private Timestamp dataHoraAtualizacao;
    private String observacoes;
    private Long relatorioTurnoId;  // Referência ao id da entidade RelatoriosTurno

}

