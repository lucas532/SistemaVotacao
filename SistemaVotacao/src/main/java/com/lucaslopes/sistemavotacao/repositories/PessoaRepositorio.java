package com.lucaslopes.sistemavotacao.repositories;

import com.lucaslopes.sistemavotacao.models.Pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepositorio extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByNomePessoa(String nomePessoa);

    Boolean existsByNomePessoa(String nomePessoa);
}