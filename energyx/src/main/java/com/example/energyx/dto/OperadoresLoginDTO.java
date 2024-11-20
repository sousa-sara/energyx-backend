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
public class OperadoresLoginDTO extends RepresentationModel<OperadoresLoginDTO> {

    private String lor;
    private String senhaOperador;

}
