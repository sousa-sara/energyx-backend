package com.example.energyx.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReatoresDTO extends RepresentationModel<ReatoresDTO> {

    private Long reatorId;
    private String nomeReator;
    private Long pressaoMax;
    private Long temperaturaMax;
    private Long radiacaoMax;
    private Long tipoReatorId;  // Referência ao id de TipoReator
    private Long localizacaoReatorId;  // Referência ao id de LocalizacaoReator

}
