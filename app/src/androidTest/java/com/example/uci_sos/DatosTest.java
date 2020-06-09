package com.example.uci_sos;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DatosTest {

    @Rule
    public ActivityTestRule<Datos> datosRule = new ActivityTestRule<>(Datos.class);
}