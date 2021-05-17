package com.lucaslopes.sistemavotacao.servicesTests;

import com.lucaslopes.sistemavotacao.helpers.RestauranteVotos;
import com.lucaslopes.sistemavotacao.helpers.Retorno;
import com.lucaslopes.sistemavotacao.models.Restaurante;
import com.lucaslopes.sistemavotacao.repositories.RestauranteRepositorio;
import com.lucaslopes.sistemavotacao.services.RestauranteServico;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestauranteServicoTest {
    @Autowired
    RestauranteServico restauranteServico;

    @MockBean
    RestauranteRepositorio restauranteRepositorio;

    @Test
    void buscarRestaurantePorNomeSucesso() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("TESTE");

        Mockito.when(restauranteRepositorio.findByNomeRestaurante("TESTE")).thenReturn(Optional.of(new Restaurante()));

        Optional<Restaurante> restauranteBuscado = restauranteServico.buscarRestaurantePorNome(restaurante);
        assertTrue(restauranteBuscado.isPresent());
    }

    @Test
    void existeRestaurantePorNomeSucesso() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("TESTE");

        Mockito.when(restauranteRepositorio.existsByNomeRestaurante("TESTE")).thenReturn(true);

        Boolean restauranteExiste = restauranteServico.existeRestaurantePorNome(restaurante);
        assertTrue(restauranteExiste);
    }

    @Test
    void listarRestaurantesSucesso() {
        Mockito.when(restauranteRepositorio.findAll()).thenReturn(List.of(new Restaurante()));
        List<Restaurante> restaurantes = restauranteServico.listarRestaurantes();
        assertFalse(restaurantes.isEmpty());
    }

    @Test
    void listarPorVotosSucesso() {
        Mockito.when(restauranteRepositorio.listByVotes()).thenReturn(List.of(new RestauranteVotos("NOME TESTE", 10L)));
        List<RestauranteVotos> restaurantesMaisVotados = restauranteServico.listarPorVotos();
        assertFalse(restaurantesMaisVotados.isEmpty());
    }

    @Test
    void salvarSucesso() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("TESTE");

        Mockito.when(restauranteRepositorio.save(restaurante)).thenReturn(restaurante);

        Retorno retorno = restauranteServico.salvar(restaurante);
        assertTrue(retorno.getOk());
        assertEquals("Restaurante registrado com sucesso.", retorno.getMensagem());
    }

    @Test
    void visitarSucesso() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("TESTE");

        Mockito.when(restauranteRepositorio.findByNomeRestaurante(restaurante.getNomeRestaurante())).thenReturn(Optional.of(new Restaurante()));
        Mockito.when(restauranteRepositorio.save(restaurante)).thenReturn(Mockito.any(Restaurante.class));

        Retorno retorno = restauranteServico.visitar(restaurante);
        assertTrue(retorno.getOk());
        assertEquals("Restaurante visitado com sucesso.", retorno.getMensagem());
    }

    @Test
    void visitarFalhaNaoEncontrado() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("TESTE");

        Mockito.when(restauranteRepositorio.findByNomeRestaurante(restaurante.getNomeRestaurante())).thenReturn(Optional.empty());

        Retorno retorno = restauranteServico.visitar(restaurante);
        assertFalse(retorno.getOk());
        assertEquals("Restaurante não encontrado na base de dados.", retorno.getMensagem());
    }

    @Test
    void visitadoUltSemanaSucesso() {
        Restaurante restaurante = new Restaurante();
        restaurante.setUltVisita(LocalDateTime.now().minusHours(TimeUnit.DAYS.toHours(7)));

        Boolean visitado = restauranteServico.visitadoUltSemana(restaurante);
        assertFalse(visitado);
    }

    @Test
    void visitadoUltSemanaFalhaJaVisitado() {
        Restaurante restaurante = new Restaurante();
        restaurante.setUltVisita(LocalDateTime.now());

        Boolean visitado = restauranteServico.visitadoUltSemana(restaurante);
        assertTrue(visitado);
    }
}