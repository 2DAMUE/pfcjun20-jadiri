package com.example.uci_sos;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.uci_sos.modelo.entidad.Camas;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.uci_sos.MetodosPersonalizados.childOf;
import static com.example.uci_sos.MetodosPersonalizados.swypeRefresh;

@RunWith(AndroidJUnit4.class)
public class MisCamasTest {

    @Rule
    public ActivityTestRule<MisCamas> misCamasRule = new ActivityTestRule<>(MisCamas.class);

    private MisCamas misCamas;

    @Before
    public void setUp() {
        misCamas = misCamasRule.getActivity();
    }

    private Camas getCama() {
        return misCamas.getCamaSeleccionada();
    }

    @Test
    public void cargaCamasPlanta() {
//        onView(childOf(childOf(withId(R.id.rootPlanta), 0), 0)).check(matches(hasImage(R.drawable.cama_roja)));
        onView(withId(R.id.rootPlanta)).check(matches(isDisplayed()));
    }

    @Test
    public void cargaCamasUCI() {
//        onView(childOf(childOf(withId(R.id.rootUCI), 0), 0)).check(matches(hasImage(R.drawable.cama_verde)));
        onView(withId(R.id.rootUCI)).check(matches(isDisplayed()));
    }

    @Test
    public void cargaCamasUrgencias() {
//        onView(childOf(childOf(withId(R.id.rootUrgencias), 0), 0)).check(matches(hasImage(R.drawable.cama_amarilla)));
        onView(withId(R.id.rootUrgencias)).check(matches(isDisplayed()));
    }

    @Test
    public void cargaDialog() {
        onView(childOf(childOf(withId(R.id.rootPlanta), 0), 0)).perform(click());
        onView(withId(R.id.btnGuardarCama)).check(matches(isDisplayed()));
    }

    @Test
    public void cargarDatosCama() {
        onView(childOf(childOf(withId(R.id.rootPlanta), 0), 0)).perform(click());
        Camas cama = getCama();
        switch (cama.getEstado()) {
            case "libre":
                onView(withId(R.id.spEstado)).check(matches(withSpinnerText("Libre")));
                break;
            case "ocupado":
                onView(withId(R.id.spEstado)).check(matches(withSpinnerText("Ocupada")));
                break;
            case "noDisponible":
                onView(withId(R.id.spEstado)).check(matches(withSpinnerText("No Disponible")));
                break;
        }
        onView(withId(R.id.spPlanta)).check(matches(withSpinnerText(cama.getPlanta())));
        onView(withId(R.id.txtIdCama)).check(matches(withText(String.valueOf(cama.getId()))));
        if (cama.isContagio())
            onView(withId(R.id.chContagio)).check(matches(isChecked()));
        else
            onView(withId(R.id.chContagio)).check(matches(isNotChecked()));
    }

    @Test
    public void cancelar() {
        onView(childOf(childOf(withId(R.id.rootPlanta), 0), 0)).perform(click());
        Camas cama = getCama();
        onView(withId(R.id.txtIdCama)).perform(clearText(), typeText("15"), closeSoftKeyboard());
        onView(withId(R.id.btnCancelarCama)).perform(click());
        onView(withId(R.id.btnCancelarCama)).check(doesNotExist());
        onView(childOf(childOf(withId(R.id.rootPlanta), 0), 0)).perform(click());
        onView(withId(R.id.txtIdCama)).check(matches(withText(String.valueOf(cama.getId()))));
    }

    @Test
    public void guardarCama() {
        onView(childOf(childOf(withId(R.id.rootPlanta), 0), 0)).perform(click());
        onView(withId(R.id.txtIdCama)).perform(clearText(), typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.btnGuardarCama)).perform(click());
        onView(childOf(childOf(withId(R.id.rootPlanta), 0), 0)).perform(click());
        onView(withId(R.id.txtIdCama)).check(matches(withText("2")));
    }

    @Test
    public void refresh() {
        onView(withId(R.id.rootPlanta)).perform(swypeRefresh(swipeDown(), isDisplayingAtLeast(50)));
        onView(childOf(childOf(withId(R.id.rootPlanta), 0), 0)).check(matches(isDisplayed()));
    }

    @Test
    public void toHospital() {
        onView(withId(R.id.btnHospitalCamas)).perform(click());
        onView(withId(R.id.graficobarras)).check(matches(isDisplayed()));
    }

    @Test
    public void toReservar() {
        onView(withId(R.id.btnReservarCamas)).perform(click());
        onView(withId(R.id.btnBuscar)).check(matches(isDisplayed()));
    }
}