package com.example.energyx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "gs_el_historico_relatorio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoRelatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hist_relatorio_id")
    private Long histRelatorioId;

    @NotNull(message = "A data e hora da atualização são obrigatórias.")
    @Column(name = "data_hora_atualizacao", nullable = false)
    private Timestamp dataHoraAtualizacao;

    @Column(name = "observacoes", length = 150)
    private String observacoes;

    @NotNull(message = "O relatório de turno é obrigatório.")
    @ManyToOne(optional = false)
    @JoinColumn(name = "relatorio_turno_id", nullable = false, foreignKey = @ForeignKey(name = "gs_el_relatorios_turno_fk"))
    private RelatoriosTurno relatorioTurno;
}
