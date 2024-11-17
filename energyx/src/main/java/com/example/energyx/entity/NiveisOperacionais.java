package com.example.energyx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "gs_el_niveis_operacionais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NiveisOperacionais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nivel_operacional_id")
    private Long nivelOperacionalId;

    @NotNull(message = "A data e hora da medição são obrigatórias.")
    @Column(name = "data_hora_medicao", nullable = false)
    private Timestamp dataHoraMedicao;

    @NotNull(message = "A pressão é obrigatória.")
    @Column(name = "pressao", nullable = false)
    private double pressao;

    @NotNull(message = "A temperatura é obrigatória.")
    @Column(name = "temperatura", nullable = false)
    private double temperatura;

    @NotNull(message = "A radiação é obrigatória.")
    @Column(name = "radiacao", nullable = false)
    private BigDecimal radiacao;

    @NotNull(message = "O reator é obrigatório.")
    @ManyToOne(optional = false)
    @JoinColumn(name = "reator_id", nullable = false, foreignKey = @ForeignKey(name = "gs_el_reatores_fk"))
    private Reatores reatores;
}