package com.lucaslopes.sistemavotacao.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity(name = "PESSOA")
public class Pessoa {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_PESSOA")
    @Id
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOME_PESSOA", updatable = false)
    private String nomePessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }
}