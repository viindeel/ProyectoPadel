package com.example.padelscore.data.model;

public class Tournament {
    private final String id;
    private final String nombre;
    private final String ubicacion;
    private final String fecha;

    public Tournament(String id, String nombre, String ubicacion, String fecha) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getFecha() {
        return fecha;
    }
}
