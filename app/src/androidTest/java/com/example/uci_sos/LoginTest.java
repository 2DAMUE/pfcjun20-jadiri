package com.example.uci_sos;

import android.content.Intent;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.PendingIntent.getActivity;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
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
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityTestRule<Login> loginRule = new ActivityTestRule<>(Login.class);

    private Login login;

    @Before
    public void setUp() throws Exception {
        login = loginRule.getActivity();
    }

    @Test
    public void sinEmail() {
        onView(withId(R.id.txtPwdLogin)).perform(typeText("123456"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withText("Por favor, rellene todos los campos"))
                .inRoot(withDecorView(not(is(login.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void sinPWD() {
        onView(withId(R.id.txtEmailLogin)).perform(typeText("maildeprueba@mail.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withText("Por favor, rellene todos los campos"))
                .inRoot(withDecorView(not(is(login.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void emailErroneo() {
        onView(withId(R.id.txtEmailLogin)).perform(typeText("maildepruebamail.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.txtPwdLogin)).perform(typeText("123456"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withText("E-mail o contraseña incorrectas"))
                .inRoot(withDecorView(not(is(login.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void pwdErronea() {
        onView(withId(R.id.txtEmailLogin)).perform(typeText("maildeprueba@mail.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.txtPwdLogin)).perform(typeText("1234567"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withText("E-mail o contraseña incorrectas"))
                .inRoot(withDecorView(not(is(login.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void emailYPwdIncorrecto() {
        onView(withId(R.id.txtEmailLogin)).perform(typeText("maildeprueba@maile.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.txtPwdLogin)).perform(typeText("1234567"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withText("E-mail o contraseña incorrectas"))
                .inRoot(withDecorView(not(is(login.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void loginCorrecto() {
        onView(withId(R.id.txtEmailLogin)).perform(typeText("maildeprueba@mail.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.txtPwdLogin)).perform(typeText("123456"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.lbl_textoCamasUrgencia)).check(matches(isDisplayed()));
    }
}