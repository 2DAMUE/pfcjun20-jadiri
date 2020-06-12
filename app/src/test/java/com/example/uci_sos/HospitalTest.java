package com.example.uci_sos;

import com.example.uci_sos.modelo.entidad.Hospital;
import com.example.uci_sos.modelo.entidad.Planta;
import com.example.uci_sos.modelo.entidad.UCI;
import com.example.uci_sos.modelo.entidad.Urgencias;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class HospitalTest {

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

    @Test
    public void nombre() {
        String input = "Gregorio Marañón";
        String output;

        Hospital h = new Hospital();
        h.setNombre(input);
        output = h.getNombre();

        assertEquals(input, output);
    }

    @Test
    public void id() {
        int input = 6;
        int output;

        Hospital h = new Hospital();
        h.setCodHospital(input);
        output = h.getCodHospital();

        assertEquals(input, output);
    }

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

    @Test
    public void addUCI() {
        UCI input = new UCI();
        UCI output;

        Hospital h = new Hospital();
        h.addCamaUCI(input);
        output = h.getListaCamasUCI().get(0);

        assertEquals(input, output);
    }

    @Test
    public void addUrgencias() {
        Urgencias input = new Urgencias();
        Urgencias output;

        Hospital h = new Hospital();
        h.addCamaUrgencias(input);
        output = h.getListaCamasUrgencias().get(0);

        assertEquals(input, output);
    }

    @Test
    public void addCamaPLanta() {
        Planta input = new Planta();
        Planta output;

        Hospital h = new Hospital();
        h.addCamaPlanta(input);
        output = h.getListaCamasPlanta().get(0);

        assertEquals(input, output);
    }

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
