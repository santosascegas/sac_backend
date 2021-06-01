package com.sac.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "usuarios_admin")
public class AdministradorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "perfil")
    private Set<Integer> perfil = new HashSet<>();

    @Column(name = "nm_usua_login", unique = true)
    private String login;

    @Column(name = "nm_usua_senha")
    private String senha;

    public AdministradorModel() {}

    public AdministradorModel(Long id, String login, String senha) {
        this.id = id;
        this.login = login;
        this.senha = senha;
    }

    public void addPerfil(TipoPerfil tipoPerfil) {
        this.perfil.add(tipoPerfil.getCod());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    @JsonIgnore
    public String getSenha() { return senha; }
    @JsonIgnore
    public void setSenha(String senha) { this.senha = senha; }
    public Set<TipoPerfil> getPerfil() {
        return perfil.stream().map(TipoPerfil::toEnum)
                .collect(Collectors.toSet());
    }
}
