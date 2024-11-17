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
public class OperadoresDTO extends RepresentationModel<OperadoresDTO> {

    private Long operadorId;
    private String nomeOperador;
    private String senhaOperador;
    private String cargo;
    private Long turnoId;  // Referência ao id de Turnos
    private String lor;

}
