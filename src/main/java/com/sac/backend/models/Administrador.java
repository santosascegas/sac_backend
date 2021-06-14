package com.sac.backend.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="tb_admin")
public class Administrador extends Usuario {
    public Administrador() {}
}
