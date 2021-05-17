package com.lucaslopes.sistemavotacao.repositoriesTests;

import com.lucaslopes.sistemavotacao.models.Restaurante;
import com.lucaslopes.sistemavotacao.helpers.RestauranteVotos;
import com.lucaslopes.sistemavotacao.repositories.RestauranteRepositorio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RestauranteRepositorioTest {
    @Autowired
    private RestauranteRepositorio restauranteRepositorio;

    //ID do restaurante incluído para ser usado nos testes
    private Long idRestauranteIncluido;

    @BeforeEach
    void incluirPreTeste() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("Nome Teste");

        restauranteRepositorio.save(restaurante);
        idRestauranteIncluido = restaurante.getId();
    }

    @Test
    void findById() {
        Optional<Restaurante> restaurante = restauranteRepositorio.findById(idRestauranteIncluido);
        assertTrue(restaurante.isPresent());
    }

    @Test
    void findByNomeRestaurante() {
        Optional<Restaurante> restaurante = restauranteRepositorio.findByNomeRestaurante("Nome Teste");
        assertTrue(restaurante.isPresent());
    }

    @Test
    void findAll() {
        List<Restaurante> restaurantes = restauranteRepositorio.findAll();
        assertFalse(restaurantes.isEmpty());
    }

    @Test
    void existsByNomeRestaurante() {
        Boolean existe = restauranteRepositorio.existsByNomeRestaurante("Nome Teste");
        assertTrue(existe);
    }

    @Test
    void saveRestaurante() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("Teste");

        restauranteRepositorio.save(restaurante);
        assertNotNull(restaurante.getId());
    }

    @Test
    void listarMaisVotados() {
        List<RestauranteVotos> resultadoVotacao = restauranteRepositorio.listByVotes();
        assertFalse(resultadoVotacao.isEmpty());
    }
}