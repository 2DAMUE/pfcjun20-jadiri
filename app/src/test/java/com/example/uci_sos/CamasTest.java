package com.example.uci_sos;

import com.example.uci_sos.modelo.entidad.Camas;
import com.example.uci_sos.modelo.entidad.Planta;
import com.example.uci_sos.modelo.entidad.UCI;
import com.example.uci_sos.modelo.entidad.Urgencias;

import org.junit.Test;

import static org.junit.Assert.*;

public class CamasTest {

    @Test
    public void estado() {
        String input = "libre";
        String output;

        Camas cama = new UCI();
        cama.setEstado(input);
        output = cama.getEstado();

        assertEquals("libre", output);
    }

    @Test
    public void id() {
        int input = 0;
        int output;

        Camas cama = new Planta();
        cama.setId(input);
        output = cama.getId();

        assertEquals(0, output);
    }

    @Test
    public void planta() {
        String input = "Planta 2";
        String output;

        Camas cama = new Urgencias();
        cama.setPlanta(input);
        output = cama.getPlanta();

        assertEquals("Planta 2", output);
    }

    @Test
    public void contagio() {
        boolean input = true;
        boolean output;

        Camas cama = new UCI();
        cama.setContagio(input);
        output = cama.isContagio();

        assertEquals(true, output);
    }
}
