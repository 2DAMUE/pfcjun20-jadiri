package com.uem.uci_sos;

import com.uem.uci_sos.modelo.entidad.Usuario;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test de la clase Usuario
 *
 * @author Ricardo Bordería Pi
 * @see Usuario
 */
public class UsuarioTest {

    /**
     * Test de los getter y setter del atributo nombre
     *
     * @see Usuario#setNombre(String)
     * @see Usuario#getNombre()
     */
    @Test
    public void nombre() {
        String input = "Ricardo";
        String output;

        Usuario u = new Usuario();
        u.setNombre(input);
        output = u.getNombre();

        assertEquals(input, output);
    }

    /**
     * Test de los getter y setter del atributo apellido
     *
     * @see Usuario#setApellido(String)
     * @see Usuario#getApellido()
     */
    @Test
    public void apellido() {
        String input = "Bordería";
        String output;

        Usuario u = new Usuario();
        u.setApellido(input);
        output = u.getApellido();

        assertEquals(input, output);
    }

    /**
     * Test de los getter y setter del atributo codHospital
     *
     * @see Usuario#setCodHospital(int)
     * @see Usuario#getCodHospital()
     */
    @Test
    public void codHospital() {
        int input = 16;
        int output;

        Usuario user = new Usuario();
        user.setCodHospital(input);
        output = user.getCodHospital();

        assertEquals(input, output);
    }
}
