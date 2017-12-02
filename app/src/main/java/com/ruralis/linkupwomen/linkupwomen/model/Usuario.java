package com.ruralis.linkupwomen.linkupwomen.model;

/**
 * Created by Ricardo Silva on 02/12/2017.
 */

public class Usuario {

    private String login;
    private String senha;
    private String nommeCompleto;
    private String email;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNommeCompleto() {
        return nommeCompleto;
    }

    public void setNommeCompleto(String nommeCompleto) {
        this.nommeCompleto = nommeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
