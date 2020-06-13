package com.example.uci_sos;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Test del activity Datos
 *
 * @author Ricardo Bordería Pi
 * @see Datos
 */
@RunWith(AndroidJUnit4.class)
public class DatosTest {

    @Rule
    public ActivityTestRule<Datos> datosRule = new ActivityTestRule<>(Datos.class);

    private Datos datos;

    @Before
    public void setUp() {
        datos = datosRule.getActivity();
    }

    /**
     * Intenta crear un hospital sin rellenar el campo de Número de Plantas.
     * Resultado esperado: Pide al usuario mediante un Toast que rellene todos los campos o
     * que rellene adecuadamente el campo de Número de Plantas
     */
    @Test
    public void sinPlantas() {
        onView(withId(R.id.txtNombreHospital)).perform(typeText("Hospital Test"), closeSoftKeyboard());
        onView(withId(R.id.btnToConfigPlantas)).perform(click());
        onView(withText("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de plantas"))
                .inRoot(withDecorView(not(is(datos.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * Intenta crear un hospital introduciendo letras en el campo de Número de Plantas.
     * Esta prueba verifica que el usuario o introduzca letras en el campo de Número de Planta aunque
     * se despliegue el teclado de números enteros y positivos.
     * Resultado esperado: Pide al usuario mediante un Toast que rellene todos los campos o
     * que rellene adecuadamente el campo de Número de Plantas
     */
    @Test
    public void plantaLetra() {
        onView(withId(R.id.txtNombreHospital)).perform(typeText("Hospital Test"), closeSoftKeyboard());
        onView(withId(R.id.txtNumPlantas)).perform(typeText("Hello there"), closeSoftKeyboard());
        onView(withId(R.id.btnToConfigPlantas)).perform(click());
        onView(withText("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de plantas"))
                .inRoot(withDecorView(not(is(datos.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * Intenta crear un hospital sin darle nombre.
     * Resultado esperado: Pide al usuario mediante un Toast que rellene todos los campos
     */
    @Test
    public void sinHospital() {
        onView(withId(R.id.txtNumPlantas)).perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.btnToConfigPlantas)).perform(click());
        onView(withText("Por favor, rellene todos los campos"))
                .inRoot(withDecorView(not(is(datos.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * Intenta crear un hospital con 0 plantas.
     * Resultado esperado: Indica al usuario mediante un Toast que debe haber al menos 1 planta
     */
    @Test
    public void ceroPlantas() {
        onView(withId(R.id.txtNombreHospital)).perform(typeText("Hospital Prueba"), closeSoftKeyboard());
        onView(withId(R.id.txtNumPlantas)).perform(typeText("0"), closeSoftKeyboard());
        onView(withId(R.id.btnToConfigPlantas)).perform(click());
        onView(withText("Debe haber al menos una planta"))
                .inRoot(withDecorView(not(is(datos.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * Muetra un Dialog antes de crear un Hospital.
     * Resultado esperado: Muestra un AlertDialog pidiendo confirmación antes de crear un hospital
     */
    @Test
    public void todoCorrecto() {
        onView(withId(R.id.txtNombreHospital)).perform(typeText("Hospital Prueba"), closeSoftKeyboard());
        onView(withId(R.id.txtNumPlantas)).perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.btnToConfigPlantas)).perform(click());
        onView(withText("Continuar")).inRoot(isDialog()).check(matches(isDisplayed()));
    }

    /**
     * Crea un hospital después de recibir la confirmación del usuario
     * Resultado esperado: crea el hospital después de que el usuario confirme la acción
     */
    @Test
    public void crearHospital() {
        onView(withId(R.id.txtNombreHospital)).perform(typeText("Hospital Prueba"), closeSoftKeyboard());
        onView(withId(R.id.txtNumPlantas)).perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.btnToConfigPlantas)).perform(click());
        onView(withText("Continuar")).inRoot(isDialog()).perform(click());
        onView(withText(R.string.siguiente)).check(matches(isDisplayed()));
    }
}