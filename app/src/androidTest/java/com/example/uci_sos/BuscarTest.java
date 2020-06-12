package com.example.uci_sos;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class BuscarTest {

    @Rule
    public ActivityTestRule<Buscar> buscarRule = new ActivityTestRule<>(Buscar.class);

    private Buscar buscar;

    @Before
    public void setUp() {
        buscar = buscarRule.getActivity();
    }

    @Test
    public void cargaHospitales() {
        onView(withId(R.id.recyclerViewBuscar)).check(matches(isDisplayed()));
    }
}