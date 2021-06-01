package com.sac.backend.DTO;

import java.io.Serializable;

public class CredencialDTO implements Serializable {
    private static final Long serialVersionUID = 1L;

    private String login;
    private String senha;

    public CredencialDTO() {}

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
