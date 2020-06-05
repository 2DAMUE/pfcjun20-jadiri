package com.example.uci_sos;

import android.widget.ImageView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SplashTest {

    @Rule
    public ActivityTestRule<Splash> splashTestRule = new ActivityTestRule<>(Splash.class);

    private Splash splash;

    @Before
    public void setUp() throws Exception {
        splash = splashTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        splash = null;
    }

    @Test
    public void logoUCISOS() {
        ImageView logo = splash.findViewById(R.id.logoLogin);

        assertNotNull(logo);
    }

    @Test
    public void logoUem() {
        ImageView logoUem = splash.findViewById(R.id.europea);

        assertNotNull(logoUem);
    }
}