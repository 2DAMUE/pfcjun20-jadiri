package com.example.uci_sos;

import com.example.uci_sos.modelo.entidad.Camas;
import com.example.uci_sos.modelo.entidad.Planta;
import com.example.uci_sos.modelo.entidad.UCI;
import com.example.uci_sos.modelo.entidad.Urgencias;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test de la clase Camas
 *
 * @author Ricardo Bordería Pi
 * @see Camas
 */
public class CamasTest {

    /**
     * Test de los getter y setter del atributo estado
     *
     * @see Camas#setEstado(String)
     * @see Camas#getEstado()
     */
    @Test
    public void estado() {
        String input = "libre";
        String output;

        Camas cama = new UCI();
        cama.setEstado(input);
        output = cama.getEstado();

        assertEquals("libre", output);
    }

    /**
     * Test de los getter y setter del atributo id
     *
     * @see Camas#setId(int)
     * @see Camas#getId()
     */
    @Test
    public void id() {
        int input = 0;
        int output;

        Camas cama = new Planta();
        cama.setId(input);
        output = cama.getId();

        assertEquals(0, output);
    }

    /**
     * Test de los getter y setter del atributo planta
     *
     * @see Camas#setPlanta(String)
     * @see Camas#getPlanta()
     */
    @Test
    public void planta() {
        String input = "Planta 2";
        String output;

        Camas cama = new Urgencias();
        cama.setPlanta(input);
        output = cama.getPlanta();

        assertEquals("Planta 2", output);
    }

    /**
     * Test de los getter y setter del atributo contagio
     *
     * @see Camas#setContagio(boolean)
     * @see Camas#isContagio()
     */
    @Test
    public void contagio() {
        boolean output;

        Camas cama = new UCI();
        cama.setContagio(true);
        output = cama.isContagio();

        assertTrue(output);
    }
}
