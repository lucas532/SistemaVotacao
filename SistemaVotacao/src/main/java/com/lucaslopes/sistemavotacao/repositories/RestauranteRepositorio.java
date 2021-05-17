package com.lucaslopes.sistemavotacao.repositories;

import com.lucaslopes.sistemavotacao.helpers.RestauranteVotos;
import com.lucaslopes.sistemavotacao.models.Restaurante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepositorio extends JpaRepository<Restaurante, Long> {
    Optional<Restaurante> findByNomeRestaurante(String nomeRestaurante);

    Boolean existsByNomeRestaurante(String nomeRestaurante);

    @Query("select new com.lucaslopes.sistemavotacao.helpers.RestauranteVotos(r.nomeRestaurante, count(v.restaurante.id)) " +
            "from RESTAURANTE as r left join VOTO v on (r.id = v.restaurante.id) " +
            "group by r.id")
    List<RestauranteVotos> listByVotes();
}