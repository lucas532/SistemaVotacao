package com.lucaslopes.sistemavotacao.helpers;

//Classe usada para facilitar mapeamento do resultado da votação posteriormente para JSON
public class RestauranteVotos {
    private String nomeRestaurante;
    private Long numVotos;

    public RestauranteVotos(String nomeRestaurante, Long numVotos) {
        this.nomeRestaurante = nomeRestaurante;
        this.numVotos = numVotos;
    }

    public String getNomeRestaurante() {
        return nomeRestaurante;
    }

    public Long getNumVotos() {
        return numVotos;
    }
}