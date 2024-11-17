package com.example.energyx.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gs_el_localizacao_reator")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocalizacaoReator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loc_reator_id")
    private Long locReatorId;

    @Column(name = "setor", length = 50, nullable = false)
    private String setor;

    @Column(name = "unidade", length = 20, nullable = false)
    private String unidade;

    @Column(name = "descricao", length = 250)
    private String descricao;
}
