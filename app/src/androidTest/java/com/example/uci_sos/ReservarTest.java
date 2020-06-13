package com.example.uci_sos;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Test del activity Reservar
 *
 * @author Ricardo Bordería Pi
 * @see Reservar
 */
@RunWith(AndroidJUnit4.class)
public class ReservarTest {

    @Rule
    public ActivityTestRule<Reservar> reservarRule = new ActivityTestRule<>(Reservar.class);

    /**
     * Lleva a la ventana de MiHospital.
     * Resultado esperado: Abre la ventana de MiHospital
     *
     * @see MiHospital
     */
    @Test
    public void toHospital() {
        onView(withId(R.id.btnHospitalReserva)).perform(click());
        onView(withId(R.id.graficobarras)).check(matches(isDisplayed()));
    }

    /**
     * Lleva a la ventana de MisCamas (desde el menú de abajo).
     * Resultado esperado: Abre la ventana de MisCamas
     *
     * @see MisCamas
     */
    @Test
    public void toMisCamas() {
        onView(withId(R.id.btnMisCamasReserva)).perform(click());
        onView(withId(R.id.rootUrgencias)).check(matches(isDisplayed()));
    }

    /**
     * Lleva a la ventana de Buscar.
     * Resultado esperado: Abre la ventana de Buscar
     *
     * @see Buscar
     */
    @Test
    public void derivar() {
        onView(withId(R.id.btnDerivar)).perform(click());
        onView(withId(R.id.recyclerViewBuscar)).check(matches(isDisplayed()));
    }

    /**
     * Lleva a la ventana de MisCamas (con el botón de Buscar Cama).
     * Resultado esperado: Abre la ventana de MisCamas
     *
     * @see MisCamas
     */
    @Test
    public void buscarCama() {
        onView(withId(R.id.btnBuscar)).perform(click());
        onView(withId(R.id.rootPlanta)).check(matches(isDisplayed()));
    }
}