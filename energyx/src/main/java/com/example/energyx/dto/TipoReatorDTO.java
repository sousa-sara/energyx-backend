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
public class TipoReatorDTO extends RepresentationModel<TipoReatorDTO> {

    private Long tipoReatorId;
    private String descricaoReator;
    private Long capacidadeEnergia;
    private String tecnologia;
    private String fabricante;

}

