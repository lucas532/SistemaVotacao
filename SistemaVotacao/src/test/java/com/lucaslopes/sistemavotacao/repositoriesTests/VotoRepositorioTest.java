package com.lucaslopes.sistemavotacao.repositoriesTests;

import com.lucaslopes.sistemavotacao.models.*;
import com.lucaslopes.sistemavotacao.repositories.VotoRepositorio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VotoRepositorioTest {
    @Autowired
    private VotoRepositorio votoRepositorio;

    @Autowired
    private EntityManager entityManager;

    //ID do voto incluído para ser usado nos testes
    private Long idVotoIncluido;

    @BeforeEach
    void incluirPreTeste() {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("Nome Teste");
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("Nome Teste");
        voto.setRestaurante(restaurante);

        //Como o restaurante não é incluído em cascata temos que inclui-lo manualmente para testar
        entityManager.persist(restaurante);

        votoRepositorio.save(voto);
        idVotoIncluido = voto.getId();
    }

    @Test
    void findById() {
        Optional<Voto> voto = votoRepositorio.findById(idVotoIncluido);
        assertTrue(voto.isPresent());
    }

    @Test
    void findAll() {
        List<Voto> votos = votoRepositorio.findAll();
        assertFalse(votos.isEmpty());
    }

    @Test
    void saveVoto() {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("PESSOA TESTE");
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("RESTAURANTE TESTE");
        voto.setRestaurante(restaurante);

        votoRepositorio.save(voto);
        assertNotNull(voto.getId());
    }
}