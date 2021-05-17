package com.lucaslopes.sistemavotacao.servicesTests;

import com.lucaslopes.sistemavotacao.models.Pessoa;
import com.lucaslopes.sistemavotacao.repositories.PessoaRepositorio;
import com.lucaslopes.sistemavotacao.services.PessoaServico;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PessoaServicoTest {
    @Autowired
    PessoaServico pessoaServico;

    @MockBean
    PessoaRepositorio pessoaRepositorio;

    @Test
    void existePessoaPorNome() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("TESTE");

        Mockito.when(pessoaRepositorio.existsByNomePessoa("TESTE")).thenReturn(true);

        Boolean pessoaExiste = pessoaServico.existePessoaPorNome(pessoa);
        assertTrue(pessoaExiste);
    }
}