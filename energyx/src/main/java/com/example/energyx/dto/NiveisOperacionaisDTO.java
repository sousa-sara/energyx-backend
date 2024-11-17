package com.example.energyx.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NiveisOperacionaisDTO extends RepresentationModel<NiveisOperacionaisDTO> {

    private Long nivelOperacionalId;
    private Timestamp dataHoraMedicao;
    private double pressao;
    private double temperatura;
    private BigDecimal radiacao;
    private Long reatorId;  // Referência ao id da entidade Reator

}
