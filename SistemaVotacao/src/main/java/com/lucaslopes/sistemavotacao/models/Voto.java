package com.lucaslopes.sistemavotacao.models;

import javax.persistence.*;

@Entity(name = "VOTO")
public class Voto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VOTO")
    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_PESSOA")
    private Pessoa pessoa;

    @OneToOne()
    @JoinColumn(name = "ID_RESTAURANTE")
    private Restaurante restaurante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }
}