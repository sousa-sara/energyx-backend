package com.example.energyx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "gs_el_notificacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notificacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificacao_id")
    private Long notificacaoId;

    @NotNull(message = "A data e hora da notificação são obrigatórias.")
    @Column(name = "data_hora_notificacao", nullable = false)
    private LocalDateTime dataHoraNotificacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_notificacao_id", nullable = false, foreignKey = @ForeignKey(name = "gs_el_status_notificacao_fk"))
    private StatusNotificacao statusNotificacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "operador_id", nullable = false, foreignKey = @ForeignKey(name = "gs_el_operadores_fk"))
    private Operadores operadores;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reator_id", nullable = false, foreignKey = @ForeignKey(name = "gs_el_reatores_fkv2"))
    private Reatores reatores;
}

