package com.lucaslopes.sistemavotacao.helpers;

public class Retorno {
    /// <summary>Indica se o método foi realizado com sucesso</summary>
    private Boolean ok;

    /// <summary>Mensagem retornada pelo método</summary>
    private String mensagem;

    /// <summary>Construtor da classe</summary>
    /// <param name="ok"></param>
    /// <param name="mensagem">Mensagem, no caso de existir</param>
    public Retorno(Boolean ok, String mensagem) {
        this.ok = ok;
        if (mensagem == null)
            this.mensagem = "Mensagem não informada";
        this.mensagem = mensagem;
    }

    public Boolean getOk() {
        return ok;
    }

    public String getMensagem() {
        return mensagem;
    }
}