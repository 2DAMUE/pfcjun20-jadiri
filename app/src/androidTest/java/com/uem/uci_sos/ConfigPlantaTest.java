package com.uem.uci_sos;


import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.uem.uci_sos.modelo.entidad.Hospital;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Test del activity ConfigPlanta
 *
 * @author Ricardo Bordería Pi
 * @see ConfigPlanta
 */
@RunWith(AndroidJUnit4.class)
public class ConfigPlantaTest {

    @Rule
    public ActivityTestRule<ConfigPlanta> configPlantaTest = new ActivityTestRule<>(ConfigPlanta.class, true, false);

    private ConfigPlanta configPlanta;

    @Before
    public void setUp() {
        Hospital h = new Hospital();
        h.setCodHospital(5);
        h.setNombre("Hospital Test");
        Intent intent = new Intent();
        intent.putExtra("hospital", h);
        configPlantaTest.launchActivity(intent);

        configPlanta = configPlantaTest.getActivity();
    }

    /**
     * Llena todos los campos correctamente
     */
    private void llenarCampos() {
        onView(withId(R.id.txtNombrePlanta)).perform(typeText("Planta 1"), closeSoftKeyboard());
        onView(withId(R.id.txtNumCamasUCILibres)).perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.txtNumCamasUCIOcupadas)).perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.txtNumCamasPlantaLibres)).perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.txtNumCamasPlantaOcupadas)).perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.txtNumCamasUrgenciasLibres)).perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.txtNumCamasUrgenciasOcupadas)).perform(typeText("2"), closeSoftKeyboard());
    }

    /**
     * Verifica que no se introduzcan letras en el campo de Camas de UCI Libres y que no esté vacío.
     * Resultado esperado: Pide al usuario mediante un Toast que rellene todos los campos
     * o que rellene correctamente el campo
     */
    @Test
    public void UCILibresMal() {
        llenarCampos();
        onView(withId(R.id.txtNumCamasUCILibres)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.btnSiguiente)).perform(click());
        onView(withText("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de UCI libres"))
                .inRoot(withDecorView(not(is(configPlanta.getWindow().getDecorView())))).check(matches(isDisplayed()));

        onView(withId(R.id.txtNumCamasUCILibres)).perform(clearText(), typeText("Jajaja XD"), closeSoftKeyboard());
        onView(withId(R.id.btnSiguiente)).perform(click());
        onView(withText("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de UCI libres"))
                .inRoot(withDecorView(not(is(configPlanta.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * Verifica que no se introduzcan letras en el campo de Camas de UCI Ocupadas y que no esté vacío.
     * Resultado esperado: Pide al usuario mediante un Toast que rellene todos los campos
     * o que rellene correctamente el campo
     */
    @Test
    public void UCIOcupadasMal() {
        llenarCampos();
        onView(withId(R.id.txtNumCamasUCIOcupadas)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.btnSiguiente)).perform(click());
        onView(withText("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de UCI ocupadas"))
                .inRoot(withDecorView(not(is(configPlanta.getWindow().getDecorView())))).check(matches(isDisplayed()));

        onView(withId(R.id.txtNumCamasUCIOcupadas)).perform(clearText(), typeText("Jajaja XD"), closeSoftKeyboard());
        onView(withId(R.id.btnSiguiente)).perform(click());
        onView(withText("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de UCI ocupadas"))
                .inRoot(withDecorView(not(is(configPlanta.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * Verifica que no se introduzcan letras en el campo de Camas de Urgencias Libres y que no esté vacío.
     * Resultado esperado: Pide al usuario mediante un Toast que rellene todos los campos
     * o que rellene correctamente el campo
     */
    @Test
    public void urgenciasLibresMal() {
        llenarCampos();
        onView(withId(R.id.txtNumCamasUrgenciasLibres)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.btnSiguiente)).perform(click());
        onView(withText("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de urgencias libres"))
                .inRoot(withDecorView(not(is(configPlanta.getWindow().getDecorView())))).check(matches(isDisplayed()));

        onView(withId(R.id.txtNumCamasUrgenciasLibres)).perform(clearText(), typeText("Jajaja XD"), closeSoftKeyboard());
        onView(withId(R.id.btnSiguiente)).perform(click());
        onView(withText("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de urgencias libres"))
                .inRoot(withDecorView(not(is(configPlanta.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * Verifica que no se introduzcan letras en el campo de Camas de Urgencias Ocupadas y que no esté vacío.
     * Resultado esperado: Pide al usuario mediante un Toast que rellene todos los campos
     * o que rellene correctamente el campo
     */
    @Test
    public void urgenciasOcupadasMal() {
        llenarCampos();
        onView(withId(R.id.txtNumCamasUrgenciasOcupadas)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.btnSiguiente)).perform(click());
        onView(withText("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de urgencias ocupadas"))
                .inRoot(withDecorView(not(is(configPlanta.getWindow().getDecorView())))).check(matches(isDisplayed()));

        onView(withId(R.id.txtNumCamasUrgenciasOcupadas)).perform(clearText(), typeText("Jajaja XD"), closeSoftKeyboard());
        onView(withId(R.id.btnSiguiente)).perform(click());
        onView(withText("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de urgencias ocupadas"))
                .inRoot(withDecorView(not(is(configPlanta.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * Verifica que no se introduzcan letras en el campo de Camas de Planta Libres y que no esté vacío.
     * Resultado esperado: Pide al usuario mediante un Toast que rellene todos los campos
     * o que rellene correctamente el campo
     */
    @Test
    public void plantaLibresMal() {
        llenarCampos();
        onView(withId(R.id.txtNumCamasPlantaLibres)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.btnSiguiente)).perform(click());
        onView(withText("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de planta libres"))
                .inRoot(withDecorView(not(is(configPlanta.getWindow().getDecorView())))).check(matches(isDisplayed()));

        onView(withId(R.id.txtNumCamasPlantaLibres)).perform(clearText(), typeText("Jajaja XD"), closeSoftKeyboard());
        onView(withId(R.id.btnSiguiente)).perform(click());
        onView(withText("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de planta libres"))
                .inRoot(withDecorView(not(is(configPlanta.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * Verifica que no se introduzcan letras en el campo de Camas de Planta Ocupadas y que no esté vacío.
     * Resultado esperado: Pide al usuario mediante un Toast que rellene todos los campos
     * o que rellene correctamente el campo
     */
    @Test
    public void plantaOcupadasMal() {
        llenarCampos();
        onView(withId(R.id.txtNumCamasPlantaOcupadas)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.btnSiguiente)).perform(click());
        onView(withText("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de planta ocupadas"))
                .inRoot(withDecorView(not(is(configPlanta.getWindow().getDecorView())))).check(matches(isDisplayed()));

        onView(withId(R.id.txtNumCamasPlantaOcupadas)).perform(clearText(), typeText("Jajaja XD"), closeSoftKeyboard());
        onView(withId(R.id.btnSiguiente)).perform(click());
        onView(withText("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de planta ocupadas"))
                .inRoot(withDecorView(not(is(configPlanta.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * Verifica que no se puede crear una planta sin un nombre.
     * Resultado esperado: Pide al usuario mediante un Toast que rellene todos los campos
     */
    @Test
    public void sinNombre() {
        llenarCampos();
        onView(withId(R.id.txtNombrePlanta)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.btnSiguiente)).perform(click());
        onView(withText("Por favor rellene todos los campos"))
                .inRoot(withDecorView(not(is(configPlanta.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * Verifica que no se puede crear una planta con los campos vacíos.
     * Resultado esperado: Pide al usuario mediante un Toast que rellene todos los campos
     */
    @Test
    public void vacio() {
        onView(withId(R.id.btnSiguiente)).perform(click());
        onView(withText("Por favor rellene todos los campos"))
                .inRoot(withDecorView(not(is(configPlanta.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}