package com.lucaslopes.sistemavotacao.repositoriesTests;

import com.lucaslopes.sistemavotacao.models.Pessoa;
import com.lucaslopes.sistemavotacao.repositories.PessoaRepositorio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PessoaRepositorioTest {
    @Autowired
    private PessoaRepositorio pessoaRepositorio;

    //ID da pessoa incluída para ser usado nos testes
    private Long idPessoaIncluida;

    @BeforeEach
    void incluirPreTeste() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("Nome Teste");

        pessoaRepositorio.save(pessoa);
        idPessoaIncluida = pessoa.getId();
    }

    @Test
    void findById() {
        Optional<Pessoa> pessoa = pessoaRepositorio.findById(idPessoaIncluida);
        assertTrue(pessoa.isPresent());
    }

    @Test
    void findByNomePessoa() {
        Optional<Pessoa> pessoa = pessoaRepositorio.findByNomePessoa("Nome Teste");
        assertTrue(pessoa.isPresent());
    }

    @Test
    void findAll() {
        List<Pessoa> pessoas = pessoaRepositorio.findAll();
        assertFalse(pessoas.isEmpty());
    }

    @Test
    void existsByNomePessoa() {
        Boolean existe = pessoaRepositorio.existsByNomePessoa("Nome Teste");
        assertTrue(existe);
    }

    @Test
    void savePessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("Teste");

        pessoaRepositorio.save(pessoa);
        assertNotNull(pessoa.getId());
    }
}