package com.example.uci_sos;

import java.util.List;

public class Hospital {
    private int id;
    private String nombre;
    private double latitud;
    private double longitud;
    private List<Camas> lista_camas;

    public Hospital(int id, String nombre, double latitud, double longitud, List<Camas> lista_camas) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.lista_camas = lista_camas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public List<Camas> getLista_camas() {
        return lista_camas;
    }

    public void setLista_camas(List<Camas> lista_camas) {
        this.lista_camas = lista_camas;
    }
}
