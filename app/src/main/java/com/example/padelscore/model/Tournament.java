package com.example.padelscore.model;

import com.google.gson.annotations.SerializedName;

public class Tournament {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String nombre;

    @SerializedName("city")
    private String ubicacion;

    @SerializedName("start_date")
    private String fecha;

    private boolean isFavorite = false;

    public Tournament(long id, String nombre, String ubicacion, String fecha) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
