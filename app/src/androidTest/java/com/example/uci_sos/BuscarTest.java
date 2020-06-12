package com.example.uci_sos;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.uci_sos.modelo.entidad.Hospital;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.uci_sos.MetodosPersonalizados.childOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;

@RunWith(AndroidJUnit4.class)
public class BuscarTest {

    @Rule
    public ActivityTestRule<Buscar> buscarRule = new ActivityTestRule<>(Buscar.class);

    private Buscar buscar;

    @Before
    public void setUp() {
        buscar = buscarRule.getActivity();
    }

    @Test
    public void cargaRecycler() {
        onView(withId(R.id.recyclerViewBuscar)).check(matches(isDisplayed()));
    }

    @Test
    public void cargarHospitales() {
        List<Hospital> listaHospitales = buscar.getListaHospitales();
        for (int i = 0; i < listaHospitales.size(); i++) {
            Hospital h = listaHospitales.get(i);
            onView(allOf(withParent(allOf(withId(R.id.linearLayoutInterno), withParent(allOf(withId(R.id.linearLayoutExterno),
                    withParent(childOf(withId(R.id.recyclerViewBuscar), i)))))), withId(R.id.lblHospital))).check(matches(withText(h.getNombre())));

            onView(allOf(withParent(allOf(withId(R.id.constraintRecycler), withParent(allOf(withId(R.id.linearLayoutInterno),
                    withParent(allOf(withId(R.id.linearLayoutExterno), withParent(childOf(withId(R.id.recyclerViewBuscar), i)))))))),
                    withId(R.id.lblUCI))).check(matches(withText(endsWith(String.valueOf(h.getCamasUciLibres())))));

            onView(allOf(withParent(allOf(withId(R.id.constraintRecycler), withParent(allOf(withId(R.id.linearLayoutInterno),
                    withParent(allOf(withId(R.id.linearLayoutExterno), withParent(childOf(withId(R.id.recyclerViewBuscar), i)))))))),
                    withId(R.id.lblUrgencias))).check(matches(withText(endsWith(String.valueOf(h.getCamasUrgenciasLibres())))));

            onView(allOf(withParent(allOf(withId(R.id.constraintRecycler), withParent(allOf(withId(R.id.linearLayoutInterno),
                    withParent(allOf(withId(R.id.linearLayoutExterno), withParent(childOf(withId(R.id.recyclerViewBuscar), i)))))))),
                    withId(R.id.lblPlanta))).check(matches(withText(endsWith(String.valueOf(h.getCamasPlantaLibres())))));

            int camasDisponibles = h.getCamasPlantaLibres() + h.getCamasUrgenciasLibres() + h.getCamasUciLibres();

            onView(allOf(withParent(allOf(withId(R.id.constraintRecycler), withParent(allOf(withId(R.id.linearLayoutInterno),
                    withParent(allOf(withId(R.id.linearLayoutExterno), withParent(childOf(withId(R.id.recyclerViewBuscar), i)))))))),
                    withId(R.id.lblDisponible))).check(matches(withText(endsWith(String.valueOf(camasDisponibles)))));
        }
    }


}