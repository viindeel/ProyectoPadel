package com.example.padelscore.data.model;

public class Player {
    private final String id;
    private final String nombre;
    private final String nacionalidad;
    private final int ranking;

    public Player(String id, String nombre, String nacionalidad, int ranking) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.ranking = ranking;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public int getRanking() {
        return ranking;
    }
}
