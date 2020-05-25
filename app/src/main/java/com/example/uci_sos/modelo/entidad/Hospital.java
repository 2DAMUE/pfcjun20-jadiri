package com.example.uci_sos.modelo.entidad;

/**
 * Contiene los datos de un hospital
 */
public class Hospital {

    /**
     * Nombre del hospital
     */
    String nombre;

    /**
     * Número de camas de UCI
     */
    int camasUCI;
    /**
     * Número de camas de planta
     */
    int camasPlanta;
    /**
     * Número de camas de urgencias
     */
    int camasUrgencias;
    /**
     * Número de camas disponibles para derivaciones de otros hospitales
     */
    int camasDisponibles;

    /**
     * Constructor de la clase
     *
     * @param nombre           nombre del hospital
     * @param camasUCI         número de camas de UCI
     * @param camasPlanta      número de camas de planta
     * @param camasUrgencias   número de camas de urgencias
     * @param camasDisponibles número de camas disponibles para derivaciones de otros hospitales
     * @see Hospital#nombre
     * @see Hospital#camasUCI
     * @see Hospital#camasPlanta
     * @see Hospital#camasUrgencias
     * @see Hospital#camasDisponibles
     */
    public Hospital(String nombre, int camasUCI, int camasPlanta, int camasUrgencias, int camasDisponibles) {
        this.nombre = nombre;
        this.camasUCI = camasUCI;
        this.camasPlanta = camasPlanta;
        this.camasUrgencias = camasUrgencias;
        this.camasDisponibles = camasDisponibles;
    }

    /**
     * Getter del nombre del hospital
     *
     * @return nombre del hospital
     * @see Hospital#nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter del número de camas de UCI
     *
     * @return número de camas de UCI
     * @see Hospital#camasUCI
     */
    public int getCamasUCI() {
        return camasUCI;
    }

    /**
     * Getter del número de camas de planta
     *
     * @return número de camas de planta
     * @see Hospital#camasPlanta
     */
    public int getCamasPlanta() {
        return camasPlanta;
    }

    /**
     * Getter del número de camas de urgencias
     *
     * @return número de camas de urgencias
     * @see Hospital#camasUrgencias
     */
    public int getCamasUrgencias() {
        return camasUrgencias;
    }

    /**
     * Geter del número de camas disponibles para derivaciones de otros hospitales
     *
     * @return número de camas disponibles para derivaciones de otros hospitales
     * @see Hospital#camasDisponibles
     */
    public int getCamasDisponibles() {
        return camasDisponibles;
    }
}
