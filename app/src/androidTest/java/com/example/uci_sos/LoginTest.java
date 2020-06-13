package com.example.uci_sos;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Test del activity Login
 *
 * @author Ricardo Bordería Pi
 * @see Login
 */
@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityTestRule<Login> loginRule = new ActivityTestRule<>(Login.class);

    private Login login;

    @Before
    public void setUp() {
        login = loginRule.getActivity();
    }

    /**
     * Intenta hacer login sin introducir E-mail.
     * Resultado esperado: Pide al usuario que se rellenen todos los campos mediante un Toast
     */
    @Test
    public void sinEmail() {
        onView(withId(R.id.txtPwdLogin)).perform(typeText("123456"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withText("Por favor, rellene todos los campos"))
                .inRoot(withDecorView(not(is(login.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * Intenta hacer login sin introducir contraseña.
     * Resultado esperado: Pide al usuario que se rellenen todos los campos mediante un Toast
     */
    @Test
    public void sinPWD() {
        onView(withId(R.id.txtEmailLogin)).perform(typeText("maildeprueba@mail.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withText("Por favor, rellene todos los campos"))
                .inRoot(withDecorView(not(is(login.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * Intenta hacer login sin rellenar ningún campo.
     * Resultado esperado: Pide al usuario que se rellenen todos los campos mediante un Toast
     */
    @Test
    public void sinCampos() {
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withText("Por favor, rellene todos los campos"))
                .inRoot(withDecorView(not(is(login.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * Intenta hacer login con un E-mail no registrado.
     * Resultado esperado: Avisa al usuario mediante un Toast de que ha introducido un E-mail o contraseña incorrectos
     */
    @Test
    public void emailErroneo() {
        onView(withId(R.id.txtEmailLogin)).perform(typeText("maildepruebamail.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.txtPwdLogin)).perform(typeText("123456"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withText("E-mail o contraseña incorrectas"))
                .inRoot(withDecorView(not(is(login.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * Intenta hacer login con una contraseña incorrecta.
     * Resultado esperado: Avisa al usuario mediante un Toast de que ha introducido un E-mail o contraseña incorrectos
     */
    @Test
    public void pwdErronea() {
        onView(withId(R.id.txtEmailLogin)).perform(typeText("maildeprueba@mail.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.txtPwdLogin)).perform(typeText("1234567"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withText("E-mail o contraseña incorrectas"))
                .inRoot(withDecorView(not(is(login.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * Intena hacer login con un E-mail y contraseña no registrados.
     * Resultado esperado: Avisa al usuario mediante un Toast de que ha introducido un E-mail o contraseña incorrectos
     */
    @Test
    public void emailYPwdIncorrecto() {
        onView(withId(R.id.txtEmailLogin)).perform(typeText("maildeprueba@maile.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.txtPwdLogin)).perform(typeText("1234567"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withText("E-mail o contraseña incorrectas"))
                .inRoot(withDecorView(not(is(login.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * Intenta hacer login con datos correctos.
     * Resultado esperado: Abre la ventana de MiHospital
     *
     * @see MiHospital
     */
    @Test
    public void loginCorrecto() {
        onView(withId(R.id.txtEmailLogin)).perform(typeText("maildeprueba@mail.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.txtPwdLogin)).perform(typeText("123456"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.lbl_textoCamasUrgencia)).check(matches(withText(R.string.camas_urgencia)));
    }

    /**
     * Lleva al usuario a la ventana de Registro.
     * Resultado esperado: Abre la ventana de Registro
     *
     * @see Registro
     */
    @Test
    public void toRegistro() {
        onView(withId(R.id.lblRegistro)).perform(click());
        onView(withId(R.id.txtPwdReg)).check(matches(isDisplayed()));
    }
}