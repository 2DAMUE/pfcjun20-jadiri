package com.example.uci_sos;

import com.example.uci_sos.modelo.entidad.Usuario;

import org.junit.Test;

import static org.junit.Assert.*;

public class UsuarioTest {

    @Test
    public void nombre() {
        String input = "Ricardo";
        String output;

        Usuario u = new Usuario();
        u.setNombre(input);
        output = u.getNombre();

        assertEquals(input, output);
    }

    @Test
    public void apellido() {
        String input = "Bordería";
        String output;

        Usuario u = new Usuario();
        u.setApellido(input);
        output = u.getApellido();

        assertEquals(input, output);
    }
}
