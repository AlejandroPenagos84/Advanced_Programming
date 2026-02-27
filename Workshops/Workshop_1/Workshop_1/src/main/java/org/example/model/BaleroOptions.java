package org.example.model;

public enum BaleroOptions {
    SIMPLE(2),
    DOBLE(10),
    VERTICAL(3),
    MARIQUITA(4),
    PUÑALADA(5),
    PURTIÑA(6),
    DOMINIO_REVES(8),
    NONE(0);

    private Integer points;

    BaleroOptions(int i) {
        this.points = i;
    }

    public Integer getPoints() {
        return this.points;
    }
}
