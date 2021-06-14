package com.sac.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_perfil")
    private Set<Integer> perfis = new HashSet<>();

    @Column(name = "nm_login", unique = true)
    private String login;

    @Column(name = "nm_senha")
    private String senha;

    public Usuario() {}

    public Usuario(Long id, String login, String senha) {
        this.id = id;
        this.login = login;
        this.senha = senha;
    }

    public Set<TipoPerfil> getPerfis() {
        return perfis.stream().map(x -> TipoPerfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPerfil(TipoPerfil tipoPerfil) {
        this.perfis.add(tipoPerfil.getCod());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    @JsonIgnore
    public String getSenha() { return senha; }
    @JsonProperty
    public void setSenha(String senha) { this.senha = senha; }
}
