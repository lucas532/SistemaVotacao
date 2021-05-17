package com.lucaslopes.sistemavotacao.repositories;

import com.lucaslopes.sistemavotacao.models.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepositorio extends JpaRepository<Voto, Long> {

}