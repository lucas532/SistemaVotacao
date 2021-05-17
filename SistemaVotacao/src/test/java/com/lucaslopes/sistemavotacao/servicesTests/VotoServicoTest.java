package com.lucaslopes.sistemavotacao.servicesTests;

import com.lucaslopes.sistemavotacao.helpers.RestauranteVotos;
import com.lucaslopes.sistemavotacao.helpers.Retorno;
import com.lucaslopes.sistemavotacao.models.*;
import com.lucaslopes.sistemavotacao.repositories.VotoRepositorio;
import com.lucaslopes.sistemavotacao.services.RestauranteServico;
import com.lucaslopes.sistemavotacao.services.VotoServico;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VotoServicoTest {
    @Autowired
    VotoServico votoServico;

    @MockBean
    VotoRepositorio votoRepositorio;

    @MockBean
    RestauranteServico restauranteServico;

    @Test
    void buscarPorIdSucesso() {
        Mockito.when(votoRepositorio.findById(10L)).thenReturn(Optional.of(new Voto()));
        Optional<Voto> voto = votoServico.buscarPorId(10L);
        assertTrue(voto.isPresent());
    }

    @Test
    void buscarPorIdFalhaInexistente() {
        Mockito.when(votoRepositorio.findById(10L)).thenReturn(Optional.empty());
        Optional<Voto> voto = votoServico.buscarPorId(10L);
        assertFalse(voto.isPresent());
    }

    @Test
    void listarVotosSucesso() {
        Mockito.when(votoRepositorio.findAll()).thenReturn(List.of(new Voto()));
        List<Voto> votos = votoServico.listarVotos();
        assertFalse(votos.isEmpty());
    }

    @Test()
    void apagarVotosSucesso() {
        votoServico.apagarVotos();
        //Verifica se votos foram apagados
        List<Voto> votos = votoServico.listarVotos();
        assertTrue(votos.isEmpty());
    }

    @Test
    void votarSucesso() {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("NOME");
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("RESTAURANTE");
        voto.setRestaurante(restaurante);

        Mockito.when(votoRepositorio.save(voto)).thenReturn(voto);
        ReflectionTestUtils.setField(votoServico, "estadoVotacao", true);

        Retorno retornoVotar = votoServico.votar(voto);
        assertTrue(retornoVotar.getOk(), retornoVotar.getMensagem());
    }

    @Test
    void votarFalhaNomePessoaNull() {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("RESTAURANTE");
        voto.setRestaurante(restaurante);

        Mockito.when(votoRepositorio.save(voto)).thenReturn(voto);
        ReflectionTestUtils.setField(votoServico, "estadoVotacao", true);

        Retorno retornoVotar = votoServico.votar(voto);
        assertFalse(retornoVotar.getOk(), retornoVotar.getMensagem());
        assertEquals("Nome da pessoa não pode estar vazio.", retornoVotar.getMensagem());
    }

    @Test
    void votarFalhaNomePessoaVazio() {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("");
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("RESTAURANTE");
        voto.setRestaurante(restaurante);

        Mockito.when(votoRepositorio.save(voto)).thenReturn(voto);
        ReflectionTestUtils.setField(votoServico, "estadoVotacao", true);

        Retorno retornoVotar = votoServico.votar(voto);
        assertFalse(retornoVotar.getOk(), retornoVotar.getMensagem());
        assertEquals("Nome da pessoa não pode estar vazio.", retornoVotar.getMensagem());
    }

    @Test
    void votarFalhaNomeRestauranteNull() {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("NOME");
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        voto.setRestaurante(restaurante);

        Mockito.when(votoRepositorio.save(voto)).thenReturn(voto);
        ReflectionTestUtils.setField(votoServico, "estadoVotacao", true);

        Retorno retornoVotar = votoServico.votar(voto);
        assertFalse(retornoVotar.getOk(), retornoVotar.getMensagem());
        assertEquals("Nome do restaurante não pode estar vazio.", retornoVotar.getMensagem());
    }

    @Test
    void votarFalhaNomeRestauranteVazio() {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("NOME");
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("");
        voto.setRestaurante(restaurante);

        Mockito.when(votoRepositorio.save(voto)).thenReturn(voto);
        ReflectionTestUtils.setField(votoServico, "estadoVotacao", true);

        Retorno retornoVotar = votoServico.votar(voto);
        assertFalse(retornoVotar.getOk(), retornoVotar.getMensagem());
        assertEquals("Nome do restaurante não pode estar vazio.", retornoVotar.getMensagem());
    }

    @Test
    void votarFalhaNomePessoaMaiorQue100() {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("*".repeat(120));
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("RESTAURANTE");
        voto.setRestaurante(restaurante);

        Mockito.when(votoRepositorio.save(voto)).thenReturn(voto);
        ReflectionTestUtils.setField(votoServico, "estadoVotacao", true);

        Retorno retornoVotar = votoServico.votar(voto);
        assertFalse(retornoVotar.getOk(), retornoVotar.getMensagem());
        assertEquals("Nome da pessoa não pode conter mais de 100 caracteres.", retornoVotar.getMensagem());
    }

    @Test
    void votarFalhaNomeRestauranteMaiorQue100() {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("NOME");
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("*".repeat(120));
        voto.setRestaurante(restaurante);

        Mockito.when(votoRepositorio.save(voto)).thenReturn(voto);
        ReflectionTestUtils.setField(votoServico, "estadoVotacao", true);

        Retorno retornoVotar = votoServico.votar(voto);
        assertFalse(retornoVotar.getOk(), retornoVotar.getMensagem());
        assertEquals("Nome do restaurante não pode conter mais de 100 caracteres.", retornoVotar.getMensagem());
    }

    @Test
    void votarFalhaRestauranteVisitado() {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("NOME");
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("RESTAURANTE");
        voto.setRestaurante(restaurante);

        Mockito.when(votoRepositorio.save(voto)).thenReturn(voto);
        Mockito.when(restauranteServico.buscarRestaurantePorNome(Mockito.any(Restaurante.class))).thenReturn(Optional.of(new Restaurante()));
        Mockito.when(restauranteServico.visitadoUltSemana(Mockito.any(Restaurante.class))).thenReturn(true);
        ReflectionTestUtils.setField(votoServico, "estadoVotacao", true);

        Retorno retornoVotar = votoServico.votar(voto);
        assertFalse(retornoVotar.getOk(), retornoVotar.getMensagem());
        assertEquals("Este restaurante já foi visitado há menos de uma semana.", retornoVotar.getMensagem());
    }

    @Test
    void votarFalhaForaDeHorario() {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("NOME");
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("RESTAURANTE");
        voto.setRestaurante(restaurante);

        Mockito.when(votoRepositorio.save(voto)).thenReturn(voto);
        ReflectionTestUtils.setField(votoServico, "estadoVotacao", false);

        Retorno retornoVotar = votoServico.votar(voto);
        assertFalse(retornoVotar.getOk(), retornoVotar.getMensagem());
        assertEquals("O período de votação é das 18:00 às 11:00 horas apenas.", retornoVotar.getMensagem());
    }

    @Test
    void obterResultadoVotacaoSucesso() {
        Mockito.when(restauranteServico.listarPorVotos()).thenReturn(List.of(new RestauranteVotos("TESTE", 10L)));
        List<RestauranteVotos> resultadoVotacao = votoServico.obterResultadoVotacao();
        assertFalse(resultadoVotacao.isEmpty());
    }

    @Test
    void obterEstadoVotacaoSucessoAberta() {
        ReflectionTestUtils.setField(votoServico, "estadoVotacao", true);
        Boolean estadoVotacao = votoServico.obterEstadoVotacao();
        assertTrue(estadoVotacao);
    }

    @Test
    void obterEstadoVotacaoSucessoFechada() {
        ReflectionTestUtils.setField(votoServico, "estadoVotacao", false);
        Boolean estadoVotacao = votoServico.obterEstadoVotacao();
        assertFalse(estadoVotacao);
    }
}