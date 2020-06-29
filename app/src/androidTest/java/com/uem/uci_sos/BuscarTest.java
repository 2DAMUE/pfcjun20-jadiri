package com.uem.uci_sos;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.uem.uci_sos.modelo.Adaptador;
import com.uem.uci_sos.modelo.entidad.Hospital;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.uem.uci_sos.MetodosPersonalizados.childOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

/**
 * Test del activity Buscar y del dialog DialogBuscar
 *
 * @author Ricardo Bordería Pi
 * @see Buscar
 * @see DialogBuscar
 */
@RunWith(AndroidJUnit4.class)
public class BuscarTest {

    @Rule
    public ActivityTestRule<Buscar> buscarRule = new ActivityTestRule<>(Buscar.class);

    private Buscar buscar;

    @Before
    public void setUp() {
        buscar = buscarRule.getActivity();
    }

    /**
     * Verifica que se despliega el RecyclerView con los hospitales. No comprueba su información
     * Resultado esperado: se despliega el RecyclerView
     *
     * @see Adaptador
     * @see Hospital
     */
    @Test
    public void cargaRecycler() {
        onView(withId(R.id.recyclerViewBuscar)).check(matches(isDisplayed()));
    }

    /**
     * Verifica que los datos del RecyclerView son corectos.
     * Resultado esperado: Los datos cargados en e RecyclerView son correctos
     *
     * @see Adaptador
     * @see Hospital
     * @see Buscar#getListaHospitales()
     */
    @Test
    public void cargarHospitales() {
        List<Hospital> listaHospitales = buscar.getListaHospitales();
        for (int i = 0; i < listaHospitales.size(); i++) {
            Hospital h = listaHospitales.get(i);
            onView(allOf(withId(R.id.lblHospital), withParent(allOf(withId(R.id.linearLayoutInterno),
                    withParent(allOf(withId(R.id.linearLayoutExterno), withParent(childOf(withId(R.id.recyclerViewBuscar), i))))))))
                    .check(matches(withText(h.getNombre())));

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

    /**
     * Verifica que se despliega el dialog de DialogBuscar. No comprueba su información.
     * Resultado esperado: Se despliega el dialog al pulsar cualquier elemento del RecyclerView
     */
    @Test
    public void dialogDerivar() {
        onView(childOf(withId(R.id.recyclerViewBuscar), 0)).perform(click());
        onView(withId(R.id.btnDerivarUCI)).check(matches(isDisplayed()));
    }

    /**
     * Verifica que el botón de Camas de UCI está inhabilitado al pulsar un hospital sin camas de UCI disponibles.
     * Resultado esperado: El botón está deshabilitado
     */
    @Test
    public void sinCamasUCI() {
        onView(childOf(withId(R.id.recyclerViewBuscar), 3)).perform(click());
        onView(withId(R.id.btnDerivarUCI)).check(matches(not(isEnabled())));
    }

    /**
     * Verifica que el botón de Camas de Planta está inhabilitado al pulsar un hospital sin camas de Planta disponibles.
     * Resultado esperado: El botón está deshabilitado
     */
    @Test
    public void sinCamasPlanta() {
        onView(childOf(withId(R.id.recyclerViewBuscar), 3)).perform(click());
        onView(withId(R.id.btnDerivarPlanta)).check(matches(not(isEnabled())));
    }

    /**
     * Verifica que el botón de Camas de Urgencias está inhabilitado al pulsar un hospital sin camas de Urgencias disponibles.
     * Resultado esperado: El botón está deshabilitado
     */
    @Test
    public void sinCamasUrgencias() {
        onView(childOf(withId(R.id.recyclerViewBuscar), 3)).perform(click());
        onView(withId(R.id.btnDerivarUrgencias)).check(matches(not(isEnabled())));
    }

    /**
     * Verifica que el DialogBuscar desaparece sin derivar al paciente.
     * Resultado esperado: Se cierra el DialogBuscar sin derivar.
     */
    @Test
    public void cancelar() {
        onView(childOf(withId(R.id.recyclerViewBuscar), 3)).perform(click());
        onView(withId(R.id.btnCancelarBuscar)).perform(click());
        onView(childOf(withId(R.id.recyclerViewBuscar), 3)).check(matches(isDisplayed()));
    }

    /**
     * Deriva al paciente a una cama de UCI.
     * Resultado esperado: Deriva al paciente a una cama de UCI
     */
    @Test
    public void derivarUCI() {
        onView(childOf(withId(R.id.recyclerViewBuscar), 3)).perform(click());
        onView(withId(R.id.btnDerivarUCI)).perform(click());
        onView(withText(startsWith("Paciente derivado al hospital:")))
                .inRoot(withDecorView(not(is(buscar.getWindow().getDecorView())))).check(matches(isDisplayed()));
        onView(withId(R.id.btnReservarHospital)).check(matches(isDisplayed()));
    }

    /**
     * Deriva al paciente a una cama de Planta.
     * Resultado esperado: Deriva al paciente a una cama de Planta
     */
    @Test
    public void derivarPlanta() {
        onView(childOf(withId(R.id.recyclerViewBuscar), 3)).perform(click());
        onView(withId(R.id.btnDerivarPlanta)).perform(click());
        onView(withText(startsWith("Paciente derivado al hospital:")))
                .inRoot(withDecorView(not(is(buscar.getWindow().getDecorView())))).check(matches(isDisplayed()));
        onView(withId(R.id.btnReservarHospital)).check(matches(isDisplayed()));
    }

    /**
     * Deriva al paciente a una cama de Urgencias.
     * Resultado esperado: Deriva al paciente a una cama de Urgencias
     */
    @Test
    public void derivarUrgencias() {
        onView(childOf(withId(R.id.recyclerViewBuscar), 3)).perform(click());
        onView(withId(R.id.btnDerivarUrgencias)).perform(click());
        onView(withText(startsWith("Paciente derivado al hospital:")))
                .inRoot(withDecorView(not(is(buscar.getWindow().getDecorView())))).check(matches(isDisplayed()));
        onView(withId(R.id.btnReservarHospital)).check(matches(isDisplayed()));
    }
}