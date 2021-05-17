package com.lucaslopes.sistemavotacao.resources;

import com.lucaslopes.sistemavotacao.helpers.RestauranteVotos;
import com.lucaslopes.sistemavotacao.helpers.Retorno;
import com.lucaslopes.sistemavotacao.models.Voto;
import com.lucaslopes.sistemavotacao.services.VotoServico;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/votos")
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class VotoRecurso {
    private VotoServico votoServico;

    public VotoRecurso(VotoServico votoServico) {
        super();
        this.votoServico = votoServico;
    }

    @GetMapping("/estado")
    public ResponseEntity<Boolean> getEstadoVotacao() {
        Boolean estado = votoServico.obterEstadoVotacao();
        return new ResponseEntity<>(estado, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RestauranteVotos>> getResultadoVotacao() {
        List<RestauranteVotos> resultado = votoServico.obterResultadoVotacao();
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Retorno> votar(@Valid @RequestBody Voto voto) {
        Retorno retornoVotar = votoServico.votar(voto);
        return new ResponseEntity<>(retornoVotar, HttpStatus.OK);
    }
}