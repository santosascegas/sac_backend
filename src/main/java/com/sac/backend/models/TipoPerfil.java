package com.sac.backend.models;

/**
 * @author Maurício Freire
 * Date 31/05/2021 at 18:21
 * Created on IntelliJ IDEA
 */

public enum TipoPerfil {
    ADMIN(1, "ROLE_ADMIN");

    private Integer cod;
    private String desc;

    TipoPerfil(int cod, String desc) {
        this.cod = cod;
        this.desc = desc;
    }

    public static TipoPerfil toEnum(Integer cod) {
        if (cod == null)
            return null;
        for (TipoPerfil tp : TipoPerfil.values())
            if (cod.equals(tp.getCod()))
                return tp;

        throw new IllegalArgumentException("Erro " + cod);
    }

    public Integer getCod() { return cod; }
    public String getDesc() { return desc; }
}
