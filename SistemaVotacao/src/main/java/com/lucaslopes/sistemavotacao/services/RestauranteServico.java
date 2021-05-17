package com.lucaslopes.sistemavotacao.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.lucaslopes.sistemavotacao.helpers.RestauranteVotos;
import com.lucaslopes.sistemavotacao.helpers.Retorno;
import org.springframework.stereotype.Service;

import com.lucaslopes.sistemavotacao.models.Restaurante;
import com.lucaslopes.sistemavotacao.repositories.RestauranteRepositorio;

@Service
public class RestauranteServico {
    private RestauranteRepositorio restauranteRepositorio;

    public RestauranteServico(RestauranteRepositorio restauranteRepositorio) {
        super();
        this.restauranteRepositorio = restauranteRepositorio;
    }

    public Optional<Restaurante> buscarRestaurantePorNome(Restaurante restaurante) {
        return restauranteRepositorio.findByNomeRestaurante(restaurante.getNomeRestaurante());
    }

    public Boolean existeRestaurantePorNome(Restaurante restaurante) {
        return restauranteRepositorio.existsByNomeRestaurante(restaurante.getNomeRestaurante());
    }

    public List<Restaurante> listarRestaurantes() {
        List<Restaurante> restaurantes;
        restaurantes = restauranteRepositorio.findAll();
        return restaurantes;
    }

    public List<RestauranteVotos> listarPorVotos() {
        return restauranteRepositorio.listByVotes();
    }

    public Retorno salvar(Restaurante restaurante) {
        restauranteRepositorio.save(restaurante);
        return new Retorno(true, "Restaurante registrado com sucesso.");
    }

    public Retorno visitar(Restaurante restaurante) {
        Optional<Restaurante> restauranteBuscado = buscarRestaurantePorNome(restaurante);
        if (restauranteBuscado.isEmpty()) {
            return new Retorno(false, "Restaurante não encontrado na base de dados.");
        }
        Restaurante restauranteAVisitar = restauranteBuscado.get();
        //Seta o campo ultVisita com a data atual
        restauranteAVisitar.setUltVisita(LocalDateTime.now());

        restauranteRepositorio.save(restauranteAVisitar);
        return new Retorno(true, "Restaurante visitado com sucesso.");
    }

    public Boolean visitadoUltSemana(Restaurante restaurante) {
        if (restaurante.getUltVisita() != null) {
            return restaurante.getUltVisita().isAfter(LocalDateTime.now().minusHours(TimeUnit.DAYS.toHours(7)));
        }
        return false;
    }
}