package com.uem.uci_sos;

import com.uem.uci_sos.modelo.entidad.Hospital;
import com.uem.uci_sos.modelo.entidad.Planta;
import com.uem.uci_sos.modelo.entidad.UCI;
import com.uem.uci_sos.modelo.entidad.Urgencias;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Test de la clase Hospital
 *
 * @author Ricardo Bordería Pi
 * @see Hospital
 */
public class HospitalTest {

    /**
     * Test del setter y getter del atributo listaCamasUCI
     *
     * @see Hospital#setListaCamasUCI(List)
     * @see Hospital#getListaCamasUCI()
     */
    @Test
    public void listaCamasUCI() {
        List<UCI> input = new ArrayList<>();
        List<UCI> output;
        input.add(new UCI());

        Hospital h = new Hospital();
        h.setListaCamasUCI(input);
        output = h.getListaCamasUCI();

        assertEquals(input, output);
    }

    /**
     * Test del setter y getter del atributo listaCamasUrgencias
     *
     * @see Hospital#setListaCamasUrgencias(List)
     * @see Hospital#getListaCamasUrgencias()
     */
    @Test
    public void listaCamasUrgencias() {
        List<Urgencias> input = new ArrayList<>();
        List<Urgencias> output;
        input.add(new Urgencias());
        input.add(new Urgencias());

        Hospital h = new Hospital();
        h.setListaCamasUrgencias(input);
        output = h.getListaCamasUrgencias();

        assertEquals(input, output);
    }

    /**
     * Test del setter y getter del atributo listaCamasPlanta
     *
     * @see Hospital#setListaCamasPlanta(List)
     * @see Hospital#getListaCamasPlanta()
     */
    @Test
    public void listaCamasPlanta() {
        List<Planta> input = new ArrayList<>();
        List<Planta> output;
        input.add(new Planta());
        input.add(new Planta());
        input.add(new Planta());

        Hospital h = new Hospital();
        h.setListaCamasPlanta(input);
        output = h.getListaCamasPlanta();

        assertEquals(input, output);
    }

    /**
     * Test del setter y getter del atributo nombre
     *
     * @see Hospital#setNombre(String)
     * @see Hospital#getNombre()
     */
    @Test
    public void nombre() {
        String input = "Gregorio Marañón";
        String output;

        Hospital h = new Hospital();
        h.setNombre(input);
        output = h.getNombre();

        assertEquals(input, output);
    }

    /**
     * Test del setter y getter del atributo codHospital
     *
     * @see Hospital#setCodHospital(int)
     * @see Hospital#getCodHospital()
     */
    @Test
    public void id() {
        int input = 6;
        int output;

        Hospital h = new Hospital();
        h.setCodHospital(input);
        output = h.getCodHospital();

        assertEquals(input, output);
    }

    /**
     * Test del setter y getter del atributo listaPlantas
     *
     * @see Hospital#setListaPlantas(List)
     * @see Hospital#getListaPlantas()
     */
    @Test
    public void listaPlantas() {
        List<String> input = new ArrayList<>();
        input.add("Planta 1");
        input.add("Planta 2");
        input.add("Planta 3");

        List<String> output;

        Hospital h = new Hospital();
        h.setListaPlantas(input);
        output = h.getListaPlantas();

        assertEquals(input, output);
    }

    /**
     * Test del método addUCI
     *
     * @see Hospital#addCamaUCI(UCI)
     */
    @Test
    public void addUCI() {
        UCI input = new UCI();
        UCI output;

        Hospital h = new Hospital();
        h.addCamaUCI(input);
        output = h.getListaCamasUCI().get(0);

        assertEquals(input, output);
    }

    /**
     * Test del método addUrgencias
     *
     * @see Hospital#addCamaUrgencias(Urgencias)
     */
    @Test
    public void addUrgencias() {
        Urgencias input = new Urgencias();
        Urgencias output;

        Hospital h = new Hospital();
        h.addCamaUrgencias(input);
        output = h.getListaCamasUrgencias().get(0);

        assertEquals(input, output);
    }

    /**
     * Test del método addPlanta
     *
     * @see Hospital#addCamaPlanta(Planta)
     */
    @Test
    public void addCamaPLanta() {
        Planta input = new Planta();
        Planta output;

        Hospital h = new Hospital();
        h.addCamaPlanta(input);
        output = h.getListaCamasPlanta().get(0);

        assertEquals(input, output);
    }

    /**
     * Test del método addPlanta()
     *
     * @see Hospital#addPlanta(String)
     */
    @Test
    public void addPlanta() {
        String input = "Planta 1";
        String output;

        Hospital h = new Hospital();
        h.addPlanta(input);
        output = h.getListaPlantas().get(0);

        assertEquals(input, output);
    }
}
