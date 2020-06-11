package com.example.uci_sos;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ConfigPlantaTest {

    @Rule
    public ActivityTestRule<ConfigPlanta> configPlantaTest = new ActivityTestRule<>(ConfigPlanta.class);

    private ConfigPlanta configPlanta;

    @Before
    public void setUp() {
        configPlanta = configPlantaTest.getActivity();
    }

    @Test
    public void comprobar() {

    }
}