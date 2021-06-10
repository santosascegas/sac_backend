package com.sac.backend.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Maurício Freire
 * Date 03/06/2021 at 17:59
 * Created on IntelliJ IDEA
 */

public class DatasModel {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data")
    private Date data;

    @Column(name = "ic_disponivel")
    private boolean status;

    public DatasModel() {}

    public DatasModel(Long id, Date data, boolean status) {
        this.id = id;
        this.data = data;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
}
