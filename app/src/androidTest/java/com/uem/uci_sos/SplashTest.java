package com.uem.uci_sos;

import android.widget.ImageView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Test del activiti Splash
 *
 * @author Ricardo Bordería Pi
 * @see Splash
 */
@RunWith(AndroidJUnit4.class)
public class SplashTest {

    @Rule
    public ActivityTestRule<Splash> splashTestRule = new ActivityTestRule<>(Splash.class);

    private Splash splash;

    @Before
    public void setUp() {
        splash = splashTestRule.getActivity();
    }

    @After
    public void tearDown() {
        splash = null;
    }

    /**
     * Verifica que el logo de UCI SOS está desplegado.
     * Resultado esperado: Depliega el logo de UCI SOS
     */
    @Test
    public void logoUCISOS() {
        ImageView logo = splash.findViewById(R.id.logoLogin);

        assertNotNull(logo);
    }

    /**
     * Verifica que el logo de la UEM está desplegado.
     * Resultado esperado: Depliega el logo de la UEM
     */
    @Test
    public void logoUem() {
        ImageView logoUem = splash.findViewById(R.id.europea);

        assertNotNull(logoUem);
    }
}