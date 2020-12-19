package com.ifma.vinculotcc_api.models.enums;

public enum PeriodoAluno {
    PRIMEIRO(1),
    SEGUNDO(2),
    TERCEIRO(3),
    QUARTO(4),
    QUINTO(5),
    SEXTO(6),
    SETIMO(7),
    OITAVO(8);

    private Integer descricao;

    private PeriodoAluno(Integer descricao) {
        this.descricao = descricao;
    }

    public Integer getDescricao() {
        return descricao;
    }

}
