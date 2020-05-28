package com.example.uci_sos.modelo.entidad;

import java.io.Serializable;

public abstract class Camas implements Serializable {

    private static final long serialVersionUID = 2L;

    private String estado;

    private int id;
    private String planta;
    private boolean contagio;

    public Camas() {

    }

    public Camas(String estado, int id, String planta, boolean contagio) {
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

    public String getPlanta() {
        return planta;
    }

    public void setPlanta(String planta) {
        this.planta = planta;
    }

    public boolean isContagio() {
        return contagio;
    }

    public void setContagio(boolean contagio) {
        this.contagio = contagio;
    }

    @Override
    public String toString() {
        return "Camas{" +
                "estado='" + estado + '\'' +
                ", id=" + id +
                ", planta='" + planta + '\'' +
                ", contagio=" + contagio +
                '}';
    }
}
