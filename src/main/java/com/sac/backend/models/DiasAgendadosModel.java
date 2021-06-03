package com.sac.backend.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name = "DiasAgendados")
public class DiasAgendadosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "dt_dias_disponiveis")
    private Date data;

    @Column(name = "ic_disponibilidade")
    private boolean disponivel;

    public DiasAgendadosModel(long id, Date data, boolean disponivel) {
        this.id = id;
        this.data = data;
        this.disponivel = disponivel;
    }

    public DiasAgendadosModel(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}
