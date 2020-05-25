package com.example.uci_sos.modelo.entidad;

import com.example.uci_sos.Login;
import com.example.uci_sos.Registro;

/**
 * Almacena la información de los usuarios de la aplicación
 *
 * @see Login
 * @see Registro
 */
public class Usuario {

    /**
     * Nombre del usuario
     */
    private String nombre;
    /**
     * Apellido del usuario
     */
    private String apellido;

    /**
     * Constructor vacío de la clase Usuario
     */
    public Usuario() {
    }

    /**
     * Constructor de la clase Usuario
     *
     * @param nombre   nombre del usuario
     * @param apellido apellido del usuario
     * @see Usuario#nombre
     * @see Usuario#apellido
     */
    public Usuario(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    /**
     * Devuelve el nombre del usuario
     *
     * @return nombre del usuario
     * @see Usuario#nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Almacena el nombre del usuario
     *
     * @param nombre nombre del usuario
     * @see Usuario#nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el apellido del usuario
     *
     * @return apellido del usuario
     * @see Usuario#apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Almacena el apellido del usuario
     *
     * @param apellido apellido del usuario
     * @see Usuario#apellido
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
