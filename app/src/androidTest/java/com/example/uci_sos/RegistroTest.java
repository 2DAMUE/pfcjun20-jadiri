package com.example.uci_sos;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RegistroTest {

    @Rule
    public ActivityTestRule<Registro> registroRule = new ActivityTestRule<>(Registro.class);

    private Registro registro;

    @Before
    public void setUp() {
        registro = registroRule.getActivity();
    }

    @After
    public void tearDown() {
        registro = null;
    }

    @Test
    public void sinCampos() {
        onView(withId(R.id.btnRegistro)).perform(click());
        onView(withText("Por favor, rellene todos los campos"))
                .inRoot(withDecorView(not(is(registro.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void sinHospital() {
        onView(withId(R.id.txtEmailRegistro)).perform(typeText("maildeprueba@mail.com"), closeSoftKeyboard());
        onView(withId(R.id.txtNombre)).perform(typeText("Ricardo"), closeSoftKeyboard());
        onView(withId(R.id.txtApellido)).perform(typeText("Borderia Pi"), closeSoftKeyboard());
        onView(withId(R.id.txtPwdReg)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPwd)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.btnRegistro)).perform(click());
        onView(withText("Por favor, seleccione un hospital"))
                .inRoot(withDecorView(not(is(registro.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void emailSinArroba() {
        onView(withId(R.id.txtEmailRegistro)).perform(typeText("maildepruebamail.com"), closeSoftKeyboard());
        onView(withId(R.id.txtNombre)).perform(typeText("Ricardo"), closeSoftKeyboard());
        onView(withId(R.id.txtApellido)).perform(typeText("Borderia Pi"), closeSoftKeyboard());
        onView(withId(R.id.txtPwdReg)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPwd)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.spinnerRegistro)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Prueba 2"))).perform(click());
        onView(withId(R.id.btnRegistro)).perform(click());
        onView(withText("Dirección de E-mail incorrecta.\nDebe contener una única @ y, al menos, un punto"))
                .inRoot(withDecorView(not(is(registro.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void emailSinPunto() {
        onView(withId(R.id.txtEmailRegistro)).perform(typeText("maildeprueba@mailcom"), closeSoftKeyboard());
        onView(withId(R.id.txtNombre)).perform(typeText("Ricardo"), closeSoftKeyboard());
        onView(withId(R.id.txtApellido)).perform(typeText("Borderia Pi"), closeSoftKeyboard());
        onView(withId(R.id.txtPwdReg)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPwd)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.spinnerRegistro)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Prueba 2"))).perform(click());
        onView(withId(R.id.btnRegistro)).perform(click());
        onView(withText("Dirección de E-mail incorrecta.\nDebe contener una única @ y, al menos, un punto"))
                .inRoot(withDecorView(not(is(registro.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void pwdDistintas() {
        onView(withId(R.id.txtEmailRegistro)).perform(typeText("maildeprueba@mail.com"), closeSoftKeyboard());
        onView(withId(R.id.txtNombre)).perform(typeText("Ricardo"), closeSoftKeyboard());
        onView(withId(R.id.txtApellido)).perform(typeText("Borderia Pi"), closeSoftKeyboard());
        onView(withId(R.id.txtPwdReg)).perform(typeText("1234567"), closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPwd)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.spinnerRegistro)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Prueba 2"))).perform(click());
        onView(withId(R.id.btnRegistro)).perform(click());
        onView(withText("Las contraseñas no coinciden."))
                .inRoot(withDecorView(not(is(registro.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void pwdCorta() {
        onView(withId(R.id.txtEmailRegistro)).perform(typeText("maildeprueba@mail.com"), closeSoftKeyboard());
        onView(withId(R.id.txtNombre)).perform(typeText("Ricardo"), closeSoftKeyboard());
        onView(withId(R.id.txtApellido)).perform(typeText("Borderia Pi"), closeSoftKeyboard());
        onView(withId(R.id.txtPwdReg)).perform(typeText("12345"), closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPwd)).perform(typeText("12345"), closeSoftKeyboard());
        onView(withId(R.id.spinnerRegistro)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Prueba 2"))).perform(click());
        onView(withId(R.id.btnRegistro)).perform(click());
        onView(withText("La contraseña debe tener, al menos 6 caracteres."))
                .inRoot(withDecorView(not(is(registro.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void pwdCortaAntesDeDistinta() {
        onView(withId(R.id.txtEmailRegistro)).perform(typeText("maildeprueba@mail.com"), closeSoftKeyboard());
        onView(withId(R.id.txtNombre)).perform(typeText("Ricardo"), closeSoftKeyboard());
        onView(withId(R.id.txtApellido)).perform(typeText("Borderia Pi"), closeSoftKeyboard());
        onView(withId(R.id.txtPwdReg)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPwd)).perform(typeText("12345"), closeSoftKeyboard());
        onView(withId(R.id.spinnerRegistro)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Prueba 2"))).perform(click());
        onView(withId(R.id.btnRegistro)).perform(click());
        onView(withText("La contraseña debe tener, al menos 6 caracteres."))
                .inRoot(withDecorView(not(is(registro.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}