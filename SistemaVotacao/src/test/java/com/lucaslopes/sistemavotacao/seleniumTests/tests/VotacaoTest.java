package com.lucaslopes.sistemavotacao.seleniumTests.tests;

import java.util.concurrent.TimeUnit;

import com.lucaslopes.sistemavotacao.seleniumTests.pageFactory.VotacaoRestaurante;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VotacaoTest {
    WebDriver driver;
    VotacaoRestaurante objPaginaVotacao;

    @BeforeAll
    static void setWebDriver() {
        System.setProperty("webdriver.edge.driver", "src/main/resources/msedgedriver.exe");
    }

    @BeforeEach
    public void setup() {
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/VotacaoRestaurante.html");
    }

    @AfterEach
    public void encerraDriver() {
        driver.quit();
    }

    @Test
    public void testVotarSucesso() {
        objPaginaVotacao = new VotacaoRestaurante(driver);
        //Verifica o titulo da home page
        assertEquals(objPaginaVotacao.getPaginaVotacaoTitulo(), "Votação de Restaurante para o almoço");

        //Vota no restaurante e obtem a string de resposta do alert
        String resposta = objPaginaVotacao.votarNoRestaurante("Lucas", "Restaurante Teste");

        //Verifica alert
        assertEquals("Voto registrado com sucesso.", resposta);
    }

    @Test
    public void testVotarFalhaJaVotou() {
        objPaginaVotacao = new VotacaoRestaurante(driver);
        //Verifica o titulo da home page
        assertEquals(objPaginaVotacao.getPaginaVotacaoTitulo(), "Votação de Restaurante para o almoço");

        //Vota no restaurante e obtem a string de resposta do alert
        String resposta = objPaginaVotacao.votarNoRestaurante("LUCAS", "Restaurante Teste");

        //Verifica alert
        assertEquals("Esta pessoa já votou.", resposta);
    }

    @Test
    public void testVotarFalhaNomeFuncionarioVazio() {
        objPaginaVotacao = new VotacaoRestaurante(driver);
        //Verifica o titulo da home page
        assertEquals(objPaginaVotacao.getPaginaVotacaoTitulo(), "Votação de Restaurante para o almoço");

        //Vota no restaurante e obtem a string de resposta do alert
        String resposta = objPaginaVotacao.votarNoRestaurante("", "Restaurante Teste");

        //Verifica alert
        assertEquals("Nome da pessoa não pode estar vazio.", resposta);
    }

    @Test
    public void testVotarFalhaNomeFuncionarioMaiorQue100() {
        objPaginaVotacao = new VotacaoRestaurante(driver);
        //Verifica o titulo da home page
        assertEquals(objPaginaVotacao.getPaginaVotacaoTitulo(), "Votação de Restaurante para o almoço");

        //Vota no restaurante e obtem a string de resposta do alert
        String resposta = objPaginaVotacao.votarNoRestaurante("*".repeat(120), "Restaurante Teste");

        //Verifica alert
        assertEquals("Nome da pessoa não pode conter mais de 100 caracteres.", resposta);
    }

    @Test
    public void testVotarFalhaNomeRestauraneVazio() {
        objPaginaVotacao = new VotacaoRestaurante(driver);
        //Verifica o titulo da home page
        assertEquals(objPaginaVotacao.getPaginaVotacaoTitulo(), "Votação de Restaurante para o almoço");

        //Vota no restaurante e obtem a string de resposta do alert
        String resposta = objPaginaVotacao.votarNoRestaurante("Lucas", "");

        //Verifica alert
        assertEquals("Nome do restaurante não pode estar vazio.", resposta);
    }

    @Test
    public void testVotarFalhaNomeRestauranteMaiorQue100() {
        objPaginaVotacao = new VotacaoRestaurante(driver);
        //Verifica o titulo da home page
        assertEquals(objPaginaVotacao.getPaginaVotacaoTitulo(), "Votação de Restaurante para o almoço");

        //Vota no restaurante e obtem a string de resposta do alert
        String resposta = objPaginaVotacao.votarNoRestaurante("Lucas", "*".repeat(120));

        //Verifica alert
        assertEquals("Nome do restaurante não pode conter mais de 100 caracteres.", resposta);
    }

    @Test
    public void testVerResultadoLista() {
        objPaginaVotacao = new VotacaoRestaurante(driver);
        //Verifica o titulo da home page
        assertEquals(objPaginaVotacao.getPaginaVotacaoTitulo(), "Votação de Restaurante para o almoço");

        //Clica no botão de ver resultado
        objPaginaVotacao.verResultado();

        Boolean textoVazioEhExibido = objPaginaVotacao.getTextoVazio().isDisplayed();
        Boolean listaResultadoEhExibida = objPaginaVotacao.getListaResultado().isDisplayed();

        //Verifica se apenas um dos elementos está sendo exibido, como deveria ser
        assertNotEquals(textoVazioEhExibido, listaResultadoEhExibida);
    }
}
