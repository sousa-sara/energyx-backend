package com.example.energyx.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gs_el_reatores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reatores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reator_id")
    private Long reatorId;

    @Column(name = "nome_reator", length = 50, nullable = false)
    private String nomeReator;

    @Column(name = "pressao_max", nullable = false)
    private Long pressaoMax;

    @Column(name = "temperatura_max", nullable = false)
    private Long temperaturaMax;

    @Column(name = "radiacao_max", nullable = false)
    private Long radiacaoMax;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_reator_id", nullable = false, foreignKey = @ForeignKey(name = "gs_el_tipo_reator_fk"))
    private TipoReator tipoReator;

    @ManyToOne(optional = false)
    @JoinColumn(name = "loc_reator_id", nullable = false, foreignKey = @ForeignKey(name = "gs_el_localizacao_reator_fk"))
    private LocalizacaoReator localizacaoReator;
}

