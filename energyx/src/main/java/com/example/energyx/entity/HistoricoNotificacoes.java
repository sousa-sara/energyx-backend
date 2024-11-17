package com.example.energyx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "gs_el_historico_notificacoes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "notificacao_id", name = "gs_el_historico_notificacoes__idx")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoNotificacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hist_notificacao_id")
    private Long histNotificacaoId;

    @NotNull(message = "A data e hora da atualização da notificação são obrigatórias.")
    @Column(name = "data_hora_atualizacao_notif", nullable = false)
    private LocalDateTime dataHoraAtualizacaoNotif;

    @Column(name = "observacao_notificacao", length = 250)
    private String observacaoNotificacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "notificacao_id", nullable = false, foreignKey = @ForeignKey(name = "gs_el_notificacoes_fk"))
    private Notificacoes notificacoes;
}

