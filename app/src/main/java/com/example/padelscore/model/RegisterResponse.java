package com.example.padelscore.model;

public class RegisterResponse {
    public int id;
    public String username;
    public String email;
    public String access;   // Token JWT si el backend lo devuelve
    public String refresh;  // Token refresh si el backend lo devuelve
    public String message;  // Mensaje opcional del backend
}
