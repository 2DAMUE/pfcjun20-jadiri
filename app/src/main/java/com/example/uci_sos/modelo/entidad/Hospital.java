package com.example.uci_sos.modelo.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contiene los datos de un hospital
 */
public class Hospital implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Lista de camas del hospital
     *
     * @see Camas
     * @see UCI
     * @see Planta
     * @see Urgencias
     */
    private List<Camas> listaCamas;

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
        listaCamas = new ArrayList<>();
    }

    /**
     * Constructor de la clase
     *
     * @param listaCamas  camas del hospital
     * @param nombre      nombre del hospital
     * @param codHospital código del hospital
     * @see Hospital#listaCamas
     * @see Hospital#nombre
     * @see Hospital#codHospital
     */
    public Hospital(List<Camas> listaCamas, String nombre, int codHospital) {
        this.listaCamas = listaCamas;
        this.nombre = nombre;
        this.codHospital = codHospital;
    }

    /**
     * Añade una cama al hospital
     *
     * @param cama cama a añadir
     */
    public void addCama(Camas cama) {
        this.listaCamas.add(cama);
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
     * Getter del List de camas
     *
     * @return lista de camas del hospital
     * @see Hospital#listaCamas
     */
    public List<Camas> getListaCamas() {
        return listaCamas;
    }

    /**
     * Almacena el List de camas del hospital
     *
     * @param listaCamas lista de las camas del hospital
     * @see Hospital#listaCamas
     */
    public void setListaCamas(List<Camas> listaCamas) {
        this.listaCamas = listaCamas;
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

    @Override
    public String toString() {
        return "Hospital{" +
                "listaCamas=" + listaCamas +
                ", nombre='" + nombre + '\'' +
                ", codHospital=" + codHospital +
                '}';
    }
}
