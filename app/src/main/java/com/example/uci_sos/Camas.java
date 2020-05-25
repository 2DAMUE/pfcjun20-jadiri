package com.example.uci_sos;

abstract class Camas {
    private String estado;
    private int id;
    private int planta;
    private boolean contagio;

    public Camas(String estado, int id, int planta, boolean contagio) {
        this.estado = estado;
        this.id = id;
        this.planta = planta;
        this.contagio = contagio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlanta() {
        return planta;
    }

    public void setPlanta(int planta) {
        this.planta = planta;
    }

    public boolean isContagio() {
        return contagio;
    }

    public void setContagio(boolean contagio) {
        this.contagio = contagio;
    }
}
