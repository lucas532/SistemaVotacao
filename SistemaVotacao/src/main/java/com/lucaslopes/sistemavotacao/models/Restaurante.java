package com.lucaslopes.sistemavotacao.models;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity(name = "RESTAURANTE")
public class Restaurante {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_RESTAURANTE")
    @Id
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOME_RESTAURANTE", updatable = false)
    private String nomeRestaurante;

    @Nullable
    @Column(name = "ULT_VISITA", insertable = false)
    private LocalDateTime ultVisita;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeRestaurante() {
        return nomeRestaurante;
    }

    public void setNomeRestaurante(String nomeRestaurante) {
        this.nomeRestaurante = nomeRestaurante;
    }

    public LocalDateTime getUltVisita() {
        return ultVisita;
    }

    public void setUltVisita(LocalDateTime ultVisita) {
        this.ultVisita = ultVisita;
    }
}