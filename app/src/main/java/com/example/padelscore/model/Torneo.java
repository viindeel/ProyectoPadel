package com.example.padelscore.model;

import com.google.gson.annotations.SerializedName;

public class Torneo {
    public int id;
    @SerializedName("name")
    public String nombre;
    @SerializedName("start_date")
    public String fecha_inicio;
    @SerializedName("end_date")
    public String fecha_fin;
    @SerializedName("location")
    public String ubicacion;
    public String logo;
    public String tier;
    public String status;
    public String url;
    public String[] cuadros_pdfs;
}
