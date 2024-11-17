package com.example.energyx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gs_el_operadores", uniqueConstraints = {
        @UniqueConstraint(columnNames = "lor", name = "gs_el_operadores_lor_un")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Operadores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operador_id")
    private Long operadorId;

    @NotBlank(message = "O nome do operador é obrigatório.")
    @Column(name = "nome_operador", nullable = false, length = 50)
    private String nomeOperador;

    @NotBlank(message = "A senha do operador é obrigatória.")
    @Column(name = "senha_operador", nullable = false, length = 50)
    private String senhaOperador;

    @NotBlank(message = "O cargo é obrigatório.")
    @Column(name = "cargo", nullable = false, length = 50)
    private String cargo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "turno_id", nullable = false, foreignKey = @ForeignKey(name = "gs_el_turnos_fk"))
    private Turnos turnos;

    @NotBlank(message = "O LOR é obrigatório.")
    @Column(name = "lor", nullable = false, length = 30)
    private String lor;
}
