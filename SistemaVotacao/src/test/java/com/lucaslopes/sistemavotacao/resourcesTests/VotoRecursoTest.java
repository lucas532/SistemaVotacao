package com.lucaslopes.sistemavotacao.resourcesTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucaslopes.sistemavotacao.helpers.Retorno;
import com.lucaslopes.sistemavotacao.models.*;

import com.lucaslopes.sistemavotacao.services.VotoServico;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class VotoRecursoTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VotoServico votoServico;

    @Test
    void getResultadoVotacaoSucesso() throws Exception {
        mvc.perform(get("/votos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getEstadoVotacaoSucesso() throws Exception {
        mvc.perform(get("/votos/estado")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void votarSucesso() throws Exception {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("NOME A");
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("RESTAURANTE TESTE");
        voto.setRestaurante(restaurante);

        ReflectionTestUtils.setField(votoServico, "estadoVotacao", true);

        MvcResult mvcResult = mvc.perform(post("/votos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Retorno respostaEsperada = new Retorno(true, "Voto registrado com sucesso.");
        String respostaRecebida = new String(mvcResult.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

        assertThat(respostaRecebida).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(respostaEsperada));
    }

    @Test
    void votarFalhaNomePessoaVazio() throws Exception {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("");
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("RESTAURANTE TESTE");
        voto.setRestaurante(restaurante);

        ReflectionTestUtils.setField(votoServico, "estadoVotacao", true);

        MvcResult mvcResult = mvc.perform(post("/votos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Retorno respostaEsperada = new Retorno(false, "Nome da pessoa não pode estar vazio.");
        String respostaRecebida = new String(mvcResult.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

        assertThat(respostaRecebida).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(respostaEsperada));
    }

    @Test
    void votarFalhaNomePessoaMaiorQue100() throws Exception {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("*".repeat(120));
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("RESTAURANTE TESTE");
        voto.setRestaurante(restaurante);

        ReflectionTestUtils.setField(votoServico, "estadoVotacao", true);

        MvcResult mvcResult = mvc.perform(post("/votos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Retorno respostaEsperada = new Retorno(false, "Nome da pessoa não pode conter mais de 100 caracteres.");
        String respostaRecebida = new String(mvcResult.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

        assertThat(respostaRecebida).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(respostaEsperada));
    }

    @Test
    void votarFalhaNomeRestauranteVazio() throws Exception {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("NOME TESTE");
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("");
        voto.setRestaurante(restaurante);

        ReflectionTestUtils.setField(votoServico, "estadoVotacao", true);

        MvcResult mvcResult = mvc.perform(post("/votos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Retorno respostaEsperada = new Retorno(false, "Nome do restaurante não pode estar vazio.");
        String respostaRecebida = new String(mvcResult.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

        assertThat(respostaRecebida).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(respostaEsperada));
    }

    @Test
    void votarFalhaNomeRestauranteMaiorQue100() throws Exception {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("NOME TESTE");
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("*".repeat(120));
        voto.setRestaurante(restaurante);

        ReflectionTestUtils.setField(votoServico, "estadoVotacao", true);

        MvcResult mvcResult = mvc.perform(post("/votos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Retorno respostaEsperada = new Retorno(false, "Nome do restaurante não pode conter mais de 100 caracteres.");
        String respostaRecebida = new String(mvcResult.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

        assertThat(respostaRecebida).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(respostaEsperada));
    }

    @Test
    void votarFalhaPessoaJaVotou() throws Exception {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("LUCAS");
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("RESTAURANTE TESTE");
        voto.setRestaurante(restaurante);

        ReflectionTestUtils.setField(votoServico, "estadoVotacao", true);

        MvcResult mvcResult = mvc.perform(post("/votos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Retorno respostaEsperada = new Retorno(false, "Esta pessoa já votou.");
        String respostaRecebida = new String(mvcResult.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

        assertThat(respostaRecebida).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(respostaEsperada));
    }

    @Test
    void votarFalhaRestauranteJaVisitado() throws Exception {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("NOME TESTE");
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("PIZZA HUT");
        voto.setRestaurante(restaurante);

        ReflectionTestUtils.setField(votoServico, "estadoVotacao", true);

        MvcResult mvcResult = mvc.perform(post("/votos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Retorno respostaEsperada = new Retorno(false, "Este restaurante já foi visitado há menos de uma semana.");
        String respostaRecebida = new String(mvcResult.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

        assertThat(respostaRecebida).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(respostaEsperada));
    }

    @Test
    void votarFalhaForaHorario() throws Exception {
        Voto voto = new Voto();

        Pessoa pessoa = new Pessoa();
        pessoa.setNomePessoa("NOME TESTE");
        voto.setPessoa(pessoa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNomeRestaurante("RESTAURANTE TESTE");
        voto.setRestaurante(restaurante);

        ReflectionTestUtils.setField(votoServico, "estadoVotacao", false);

        MvcResult mvcResult = mvc.perform(post("/votos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Retorno respostaEsperada = new Retorno(false, "O período de votação é das 18:00 às 11:00 horas apenas.");
        String respostaRecebida = new String(mvcResult.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

        assertThat(respostaRecebida).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(respostaEsperada));
    }
}