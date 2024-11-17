package com.example.energyx.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TurnosDTO extends RepresentationModel<TurnosDTO> {

    private Long turnoId;
    private Date dataInicio;
    private Date dataFim;

}
