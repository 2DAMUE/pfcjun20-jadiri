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

@RunWith(AndroidJUnit4.class)
public class MiHospitalTest {

    @Rule
    public ActivityTestRule<MiHospital> miHospitalRule = new ActivityTestRule<>(MiHospital.class);

    @Test
    public void toReservar() {
        onView(withId(R.id.btnReservarHospital)).perform(click());
        onView(withId(R.id.btnDerivar)).check(matches(isDisplayed()));
    }

    @Test
    public void toMisCamas() {
        onView(withId(R.id.btnMisCamasHospital)).perform(click());
        onView(withId(R.id.rootPlanta)).check(matches(isDisplayed()));
    }

    @Test
    public void chart() {
        onView(withId(R.id.graficobarras)).check(matches(isDisplayed()));
    }
}