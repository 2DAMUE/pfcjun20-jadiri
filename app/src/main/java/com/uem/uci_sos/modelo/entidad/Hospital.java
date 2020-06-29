package com.uem.uci_sos.modelo.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contiene los datos de un hospital
 */
public class Hospital implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Lista de camas de UCI del hospital
     *
     * @see Camas
     * @see UCI
     */
    private List<UCI> listaCamasUCI;
    /**
     * Lista de camas de planta del hospital
     *
     * @see Camas
     * @see Planta
     */
    private List<Planta> listaCamasPlanta;
    /**
     * Lista de camas de urgencias del hospital
     *
     * @see Camas
     * @see Urgencias
     */
    private List<Urgencias> listaCamasUrgencias;

    /**
     * Lista con los nombres de las plantas del hospital
     */
    private List<String> listaPlantas;

    /**
     * Nombre del hospital
     */
    private String nombre;

    /**
     * Código que identifica a cada hospital. Éste debe se único
     */
    private int codHospital;

    /**
     * Constructor vacío de la calse
     */
    public Hospital() {
        listaCamasUCI = new ArrayList<>();
        listaCamasPlanta = new ArrayList<>();
        listaCamasUrgencias = new ArrayList<>();
        listaPlantas = new ArrayList<>();
    }

    /**
     * Constructor de la clase
     *
     * @param listaCamasUCI       List de objeto UCI
     * @param listaCamasPlanta    List de objetos Planta
     * @param listaCamasUrgencias List de objetos Urgencias
     * @param listaPlantas        List de String con el nombre de las plantas de los hospitales
     * @param nombre              Nombre del hospital
     * @param codHospital         código únido del hospital
     * @see Hospital#listaCamasUCI
     * @see Hospital#listaCamasPlanta
     * @see Hospital#listaCamasUrgencias
     * @see Hospital#listaPlantas
     * @see Hospital#nombre
     * @see Hospital#codHospital
     */
    public Hospital(List<UCI> listaCamasUCI, List<Planta> listaCamasPlanta, List<Urgencias> listaCamasUrgencias,
                    List<String> listaPlantas, String nombre, int codHospital) {
        this.listaCamasUCI = listaCamasUCI;
        this.listaCamasPlanta = listaCamasPlanta;
        this.listaCamasUrgencias = listaCamasUrgencias;
        this.nombre = nombre;
        this.codHospital = codHospital;
        this.listaPlantas = listaPlantas;
    }

    /**
     * Añade una cama de UCI al hospital
     *
     * @param cama cama de UCI a añadir
     * @see Hospital#listaCamasUCI
     */
    public void addCamaUCI(UCI cama) {
        this.listaCamasUCI.add(cama);
    }

    /**
     * Añade una cama de planta al hospital
     *
     * @param cama cama de Planta a añadir
     * @see Hospital#listaCamasPlanta
     */
    public void addCamaPlanta(Planta cama) {
        this.listaCamasPlanta.add(cama);
    }

    /**
     * Añade una cama de urgencias al hospital
     *
     * @param cama cama de Urgencias a añadir
     * @see Hospital#listaCamasUrgencias
     */
    public void addCamaUrgencias(Urgencias cama) {
        this.listaCamasUrgencias.add(cama);
    }

    /**
     * Añade el nombre de una planta del hospital
     *
     * @param planta nombre de la planta del hospital
     */
    public void addPlanta(String planta) {
        this.listaPlantas.add(planta);
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
     * Almacena el nombr del hospital
     *
     * @param nombre nombre del hospital
     * @see Hospital#nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter del List de camas de UCI
     *
     * @return lista de camas de UCI del hospital
     * @see Hospital#listaCamasUCI
     */
    public List<UCI> getListaCamasUCI() {
        return listaCamasUCI;
    }

    /**
     * Almacena el List de camas del hospital
     *
     * @param listaCamasUCI lista de las camas del hospital
     * @see Hospital#listaCamasUCI
     */
    public void setListaCamasUCI(List<UCI> listaCamasUCI) {
        this.listaCamasUCI = listaCamasUCI;
    }

    /**
     * Devuelve el nombre de las plantas del hospital
     *
     * @return List de String con el nombre de las plantas del hospital
     */
    public List<String> getListaPlantas() {
        return listaPlantas;
    }

    /**
     * Almacena los nombres de las plantas del hospital
     *
     * @param listaPlantas List de String con los nombres de las plantas
     */
    public void setListaPlantas(List<String> listaPlantas) {
        this.listaPlantas = listaPlantas;
    }

    /**
     * Getter del código del hospital
     *
     * @return código único del hospital
     * @see Hospital#codHospital
     */
    public int getCodHospital() {
        return codHospital;
    }

    /**
     * Almacena el código del hospital
     *
     * @param codHospital código único del hospital
     * @see Hospital#codHospital
     */
    public void setCodHospital(int codHospital) {
        this.codHospital = codHospital;
    }

    /**
     * Getter del Lista de camas de planta del hospital
     *
     * @return lista de camas de planta del hospital
     * @see Hospital#listaCamasPlanta
     */
    public List<Planta> getListaCamasPlanta() {
        return listaCamasPlanta;
    }

    /**
     * Almacena las camas de planta del hospital
     *
     * @param listaCamasPlanta List de camas de Planta
     * @see Hospital#listaCamasPlanta
     */
    public void setListaCamasPlanta(List<Planta> listaCamasPlanta) {
        this.listaCamasPlanta = listaCamasPlanta;
    }

    /**
     * Getter del List de camas de Urgencias del hospital
     *
     * @return lista de camas de urgencias del hospital
     * @see Hospital#listaCamasUrgencias
     */
    public List<Urgencias> getListaCamasUrgencias() {
        return listaCamasUrgencias;
    }

    /**
     * Almacena el List de camas de Urgencias del hospital
     *
     * @param listaCamasUrgencias lista de camas de urgencias
     * @see Hospital#listaCamasUrgencias
     */
    public void setListaCamasUrgencias(List<Urgencias> listaCamasUrgencias) {
        this.listaCamasUrgencias = listaCamasUrgencias;
    }

    /**
     * Devuelve el número de camas de UCI libres
     *
     * @return número de camas de UCI libres
     * @see Camas
     * @see UCI
     */
    public int getCamasUciLibres() {
        int camasLibres = 0;
        for (UCI cama : this.listaCamasUCI) {
            if (cama.getEstado().equals("libre"))
                camasLibres++;
        }
        return camasLibres;
    }

    /**
     * Devuelve el número de camas de Planta libres
     *
     * @return número de camas de Planta libres
     * @see Camas
     * @see Planta
     */
    public int getCamasPlantaLibres() {
        int camasLibres = 0;
        for (Planta cama : this.listaCamasPlanta) {
            if (cama.getEstado().equals("libre"))
                camasLibres++;
        }
        return camasLibres;
    }

    /**
     * Devuelve el número de camas de Urgencias libres
     *
     * @return número de camas de Urgencias libres
     * @see Camas
     * @see Urgencias
     */
    public int getCamasUrgenciasLibres() {
        int camasLibres = 0;
        for (Urgencias cama : this.listaCamasUrgencias) {
            if (cama.getEstado().equals("libre"))
                camasLibres++;
        }
        return camasLibres;
    }

    /**
     * Devuelve el número de camas de UCI ocupadas
     *
     * @return número de camas de UCI ocupadas
     * @see Camas
     * @see UCI
     */
    public int getCamasUCIOcupadas() {
        int camasOcupadas = 0;
        for (UCI cama : this.listaCamasUCI) {
            if (cama.getEstado().equals("ocupado"))
                camasOcupadas++;
        }
        return camasOcupadas;
    }

    /**
     * Devuelve el número de camas de Planta ocupadas
     *
     * @return número de camas de Planta ocupadas
     * @see Camas
     * @see Planta
     */
    public int getCamasPlantaOcupadas() {
        int camasOcupadas = 0;
        for (Planta cama : this.listaCamasPlanta) {
            if (cama.getEstado().equals("ocupado"))
                camasOcupadas++;
        }
        return camasOcupadas;
    }

    /**
     * Devuelve el número de camas de Urgencias ocupadas
     *
     * @return número de camas de Urgencias ocupadas
     * @see Camas
     * @see Urgencias
     */
    public int getCamasUrgenciasOcupadas() {
        int camasOcupadas = 0;
        for (Urgencias cama : this.listaCamasUrgencias) {
            if (cama.getEstado().equals("ocupado"))
                camasOcupadas++;
        }
        return camasOcupadas;
    }

    /**
     * Devuelve el número de camas de UCI no disponibles
     *
     * @return número de camas de UCI no disponibles
     * @see Camas
     * @see UCI
     */
    public int getCamasUCINoDisponibles() {
        int camasNoDisponibles = 0;
        for (UCI cama : this.listaCamasUCI) {
            if (cama.getEstado().equals("noDisponible"))
                camasNoDisponibles++;
        }
        return camasNoDisponibles;
    }

    /**
     * Devuelve el número de camas de Planta no disponibles
     *
     * @return número de camas de Planta no disponibles
     * @see Camas
     * @see Planta
     */
    public int getCamasPlantaNoDisponibles() {
        int camasNoDisponibles = 0;
        for (Planta cama : this.listaCamasPlanta) {
            if (cama.getEstado().equals("noDisponible"))
                camasNoDisponibles++;
        }
        return camasNoDisponibles;
    }

    /**
     * Devuelve el número de camas de Urgencias no disponibles
     *
     * @return número de camas de Urgencias no disponibles
     * @see Camas
     * @see Urgencias
     */
    public int getCamasUrgenciasNoDisponibles() {
        int camasNoDisponibles = 0;
        for (Urgencias cama : this.listaCamasUrgencias) {
            if (cama.getEstado().equals("noDisponible"))
                camasNoDisponibles++;
        }
        return camasNoDisponibles;
    }

    /**
     * Devuelve la suma de las camas libres del hospital
     *
     * @return todas las camas libres del hospital
     * @see Hospital#getCamasPlantaLibres()
     * @see Hospital#getCamasUciLibres()
     * @see Hospital#getCamasUrgenciasLibres()
     */
    public int getCamasDisponibles() {
        return getCamasPlantaLibres() + getCamasUciLibres() + getCamasUrgenciasLibres();
    }
}
