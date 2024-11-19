package com.example.energyx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gs_el_turnos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Turnos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "turno_id")
    private Long turnoId;

    @NotNull(message = "A descrição é obrigatória.")
    @Column(name = "descricao_turno", nullable = false, length = 50)
    private String descricaoTurno;

}

