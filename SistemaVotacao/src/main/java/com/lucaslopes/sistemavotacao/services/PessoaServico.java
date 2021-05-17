package com.lucaslopes.sistemavotacao.services;

import org.springframework.stereotype.Service;

import com.lucaslopes.sistemavotacao.models.Pessoa;
import com.lucaslopes.sistemavotacao.repositories.PessoaRepositorio;

@Service
public class PessoaServico {
    private PessoaRepositorio pessoaRepositorio;

    public PessoaServico(PessoaRepositorio pessoaRepositorio) {
        super();
        this.pessoaRepositorio = pessoaRepositorio;
    }

    public Boolean existePessoaPorNome(Pessoa pessoa) {
        return pessoaRepositorio.existsByNomePessoa(pessoa.getNomePessoa());
    }
}