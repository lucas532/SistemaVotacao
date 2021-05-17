package com.lucaslopes.sistemavotacao.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.lucaslopes.sistemavotacao.helpers.RestauranteVotos;
import com.lucaslopes.sistemavotacao.helpers.Retorno;
import org.springframework.stereotype.Service;

import com.lucaslopes.sistemavotacao.models.*;
import com.lucaslopes.sistemavotacao.repositories.VotoRepositorio;

@Service
public class VotoServico {
    private VotoRepositorio votoRepositorio;
    private PessoaServico pessoaServico;
    private RestauranteServico restauranteServico;

    private Boolean estadoVotacao = true; //true - aberta, false - fechada

    public VotoServico(VotoRepositorio votoRepositorio, PessoaServico pessoaServico, RestauranteServico restauranteServico) {
        super();
        this.votoRepositorio = votoRepositorio;
        this.pessoaServico = pessoaServico;
        this.restauranteServico = restauranteServico;
        executarThreads();
    }

    void executarThreads() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        LocalDateTime horarioAtual = LocalDateTime.now();
        long delayDiario = TimeUnit.HOURS.toSeconds(24);
        long delayInicial;

        //Apaga os votos e usuarios do banco de dados todos os dias às 18 horas e abre a votação para o dia seguinte
        LocalDateTime horarioResetDados = LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0, 0));
        if (horarioAtual.isBefore(horarioResetDados)) {
            delayInicial = ChronoUnit.SECONDS.between(horarioAtual, horarioResetDados);
        } else {
            delayInicial = ChronoUnit.SECONDS.between(horarioAtual, horarioResetDados.plusDays(1));
        }
        executor.scheduleAtFixedRate(resetarBancoDados, delayInicial, delayDiario, TimeUnit.SECONDS);

        //Encerra votação atual todos os dias às 11 horas da manhã
        LocalDateTime horarioEncerraVotaccao = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0, 0));
        if (horarioAtual.isBefore(horarioEncerraVotaccao)) {
            delayInicial = ChronoUnit.SECONDS.between(horarioAtual, horarioEncerraVotaccao);
        } else {
            delayInicial = ChronoUnit.SECONDS.between(horarioAtual, horarioEncerraVotaccao.plusDays(1));
        }
        executor.scheduleAtFixedRate(encerrarVotacao, delayInicial, delayDiario, TimeUnit.SECONDS);
    }

    Runnable resetarBancoDados = () -> {
        //Apaga os votos e as pessoas que votaram
        apagarVotos();
        //Abre votação
        estadoVotacao = true;
    };

    Runnable encerrarVotacao = () -> {
        //Encerra votação
        estadoVotacao = false;
        //Busca restaurante vencedor
        List<RestauranteVotos> maisVotados = obterResultadoVotacao();
        Restaurante vencedor = new Restaurante();
        vencedor.setNomeRestaurante(maisVotados.stream().findFirst().get().getNomeRestaurante());
        //Avisa usuários sobre o resultado do dia atual
        avisarEscolha(vencedor);
        //Marca o restaurante vencedor como visitado
        restauranteServico.visitar(vencedor);
    };

    public Optional<Voto> buscarPorId(Long id) {
        Optional<Voto> voto;
        voto = votoRepositorio.findById(id);
        return voto;
    }

    public List<Voto> listarVotos() {
        List<Voto> votos;
        votos = votoRepositorio.findAll();
        return votos;
    }

    public void apagarVotos() {
        votoRepositorio.deleteAll();
    }

    public Retorno votar(Voto voto) {
        //Validações
        if (!estadoVotacao) {
            return new Retorno(false, "O período de votação é das 18:00 às 11:00 horas apenas.");
        }
        Pessoa pessoa = voto.getPessoa();
        Restaurante restaurante = voto.getRestaurante();
        String nomePessoa = pessoa.getNomePessoa();
        String nomeRestaurante = restaurante.getNomeRestaurante();
        if (nomePessoa == null || nomePessoa.isEmpty()) {
            return new Retorno(false, "Nome da pessoa não pode estar vazio.");
        } else if (nomePessoa.length() > 100) {
            return new Retorno(false, "Nome da pessoa não pode conter mais de 100 caracteres.");
        }
        if (nomeRestaurante == null || nomeRestaurante.isEmpty()) {
            return new Retorno(false, "Nome do restaurante não pode estar vazio.");
        } else if (nomeRestaurante.length() > 100) {
            return new Retorno(false, "Nome do restaurante não pode conter mais de 100 caracteres.");
        }
        //Se pessoa já existe na base de dados significa que já votou
        if (pessoaServico.existePessoaPorNome(pessoa)) {
            return new Retorno(false, "Esta pessoa já votou.");
        }
        //Só é necessário incluir novo restaurante caso ele ainda não exista na base de dados, diferentemente da
        //pessoa essa inclusão deve ser feita manualmente já que o Restaurante não está marcado com Cascade no JPA
        Optional<Restaurante> restauranteExiste = restauranteServico.buscarRestaurantePorNome(restaurante);
        if (restauranteExiste.isPresent()) {
            //Verifica se restaurante já não foi visitado há menos de uma semana
            Boolean visitado = restauranteServico.visitadoUltSemana(restauranteExiste.get());
            if (visitado) {
                return new Retorno(false, "Este restaurante já foi visitado há menos de uma semana.");
            }
            voto.setRestaurante(restauranteExiste.get());
        }
        restauranteServico.salvar(restaurante);
        votoRepositorio.save(voto);
        return new Retorno(true, "Voto registrado com sucesso.");
    }

    public List<RestauranteVotos> obterResultadoVotacao() {
        List<RestauranteVotos> resultadoVotacao = restauranteServico.listarPorVotos();
        //Caso haja mais de 3 restaurantes retorna apenas os três mais votados
        if (resultadoVotacao.size() > 3) {
            resultadoVotacao.subList(3, resultadoVotacao.size()).clear();
        }
        return resultadoVotacao;
    }

    public Boolean obterEstadoVotacao() {
        return estadoVotacao;
    }

    public void avisarEscolha(Restaurante vencedor) {
        //Método fictício que avisaria o funcionário sobre a escolha do restaurante
        //Poderia ser uma notificação, email, SMS, Whats, etc...
    }
}