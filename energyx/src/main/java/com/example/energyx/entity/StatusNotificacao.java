package com.example.energyx.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gs_el_status_notificacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusNotificacao {

    @Id
    @Column(name = "status_notificacao_id")
    private Long statusNotificacaoId;

    @Column(name = "descr_status", length = 200)
    private String descrStatus;
}