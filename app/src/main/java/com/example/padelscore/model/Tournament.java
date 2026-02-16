package com.example.padelscore.model;

import com.google.gson.annotations.SerializedName;

public class Tournament {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName(value = "location", alternate = {"city"})
    private String location;

    @SerializedName("country")
    private String country;

    @SerializedName(value = "startDate", alternate = {"start_date"})
    private String startDate;

    @SerializedName(value = "endDate", alternate = {"end_date"})
    private String endDate;

    @SerializedName("status")
    private String status;

    private boolean isFavorite = false;

    public Tournament(String id, String name, String location, String startDate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.startDate = startDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return name;
    }

    public void setNombre(String nombre) {
        this.name = nombre;
    }

    public String getUbicacion() {
        return location;
    }

    public void setUbicacion(String ubicacion) {
        this.location = ubicacion;
    }

    public String getFecha() {
        return startDate;
    }

    public void setFecha(String fecha) {
        this.startDate = fecha;
    }

    public String getFechaFin() {
        return endDate;
    }

    public void setFechaFin(String fechaFin) {
        this.endDate = fechaFin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
