package com.lucaslopes.sistemavotacao.seleniumTests.pageFactory;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VotacaoRestaurante {
    WebDriver driver;

    @FindBy(id = "tituloTexto")
    WebElement paginaVotacaoTitulo;

    @FindBy(id = "nomeFuncionario")
    WebElement nomeFuncionario;

    @FindBy(id = "nomeRestaurante")
    WebElement nomeRestaurante;

    @FindBy(id = "btnVotar")
    WebElement votar;

    @FindBy(id = "btnResultado")
    WebElement resultado;

    @FindBy(id = "textoVazio")
    WebElement textoVazio;

    @FindBy(id = "listaResult")
    WebElement listaResultado;

    public VotacaoRestaurante(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Busca o titulo da página de votação
    public String getPaginaVotacaoTitulo() {
        return paginaVotacaoTitulo.getText();
    }

    //Seta nome funcionário na textbox
    public void setNomeFuncionario(String strNomeFuncionario) {
        nomeFuncionario.sendKeys(strNomeFuncionario);
    }

    //Seta nome restaurante na textbox
    public void setNomeRestaurante(String strNomeRestaurante) {
        nomeRestaurante.sendKeys(strNomeRestaurante);
    }

    //Clica no botão votar
    public void clickVotar() {
        votar.click();
    }

    public void clickResultado() {
        resultado.click();
    }

    public WebElement getTextoVazio() {
        return textoVazio;
    }

    public WebElement getListaResultado() {
        return listaResultado;
    }

    public String votarNoRestaurante(String strNomeFuncionario, String strNomeRestaurante) {
        //Preenche nome funcionário
        this.setNomeFuncionario(strNomeFuncionario);
        //Preenche nome restaurante
        this.setNomeRestaurante(strNomeRestaurante);
        //Clica no botão votar
        this.clickVotar();
        //Espera por presença do alert
        String resposta;
        try {
            WebDriverWait wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.alertIsPresent());
            //Procura pelo alert
            Alert alert = driver.switchTo().alert();
            //Pega mensagem do alert
            resposta = alert.getText();
            alert.accept();
        } catch (NoAlertPresentException e) {
            resposta = e.getMessage();
        }
        return resposta;
    }

    public void verResultado() {
        this.clickResultado();
    }
}