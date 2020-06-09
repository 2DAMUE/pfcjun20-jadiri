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

@RunWith(AndroidJUnit4.class)
public class DatosTest {

    @Rule
    public ActivityTestRule<Datos> datosRule = new ActivityTestRule<>(Datos.class);

    private Datos datos;

    @Before
    public void setUp() {
        datos = datosRule.getActivity();
    }

    @Test
    public void sinPlantas() {
        onView(withId(R.id.txtNombreHospital)).perform(typeText("Hospital Test"), closeSoftKeyboard());
        onView(withId(R.id.btnToConfigPlantas)).perform(click());
        onView(withText("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de plantas"))
                .inRoot(withDecorView(not(is(datos.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void plantaLetra() {
        onView(withId(R.id.txtNombreHospital)).perform(typeText("Hospital Test"), closeSoftKeyboard());
        onView(withId(R.id.txtNumPlantas)).perform(typeText("Hello there"), closeSoftKeyboard());
        onView(withId(R.id.btnToConfigPlantas)).perform(click());
        onView(withText("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de plantas"))
                .inRoot(withDecorView(not(is(datos.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void sinHospital() {
        onView(withId(R.id.txtNumPlantas)).perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.btnToConfigPlantas)).perform(click());
        onView(withText("Por favor, rellene todos los campos"))
                .inRoot(withDecorView(not(is(datos.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void ceroPlantas() {
        onView(withId(R.id.txtNombreHospital)).perform(typeText("Hospital Prueba"), closeSoftKeyboard());
        onView(withId(R.id.txtNumPlantas)).perform(typeText("0"), closeSoftKeyboard());
        onView(withId(R.id.btnToConfigPlantas)).perform(click());
        onView(withText("Debe haber al menos una planta"))
                .inRoot(withDecorView(not(is(datos.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void todoCorrecto() {
        onView(withId(R.id.txtNombreHospital)).perform(typeText("Hospital Prueba"), closeSoftKeyboard());
        onView(withId(R.id.txtNumPlantas)).perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.btnToConfigPlantas)).perform(click());
        onView(withText("Continuar")).inRoot(isDialog()).check(matches(isDisplayed()));
    }

    @Test
    public void crearHospital() {
        onView(withId(R.id.txtNombreHospital)).perform(typeText("Hospital Prueba"), closeSoftKeyboard());
        onView(withId(R.id.txtNumPlantas)).perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.btnToConfigPlantas)).perform(click());
        onView(withText("Continuar")).inRoot(isDialog()).perform(click());
        onView(withText(R.string.siguiente)).check(matches(isDisplayed()));
    }
}