package com.example.fernanda.ollainteligente.dto;

public class Comida {
    private  String id, titulo, cantidad,medida;
    private int tiempo;
    public Comida( ) { }

    public Comida(String id,String titulo, int tiempo, String cantidad, String medida) {
        this.id= id;
        this.titulo = titulo;
        this.tiempo = tiempo;
        this.cantidad = cantidad;
        this.medida = medida;
    }
    public Comida(String titulo, int tiempo, String cantidad, String medida) {
        this.titulo = titulo;
        this.tiempo = tiempo;
        this.cantidad = cantidad;
        this.medida = medida;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }
}