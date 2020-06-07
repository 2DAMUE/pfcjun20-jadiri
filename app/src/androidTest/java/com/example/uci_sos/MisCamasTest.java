package com.example.uci_sos;


import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MisCamasTest {

    @Rule
    public ActivityTestRule<MisCamas> misCamasRule = new ActivityTestRule<>(MisCamas.class);

    public static TypeSafeMatcher<View> hasImage(final int id) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                ImageView img = (ImageView) item;
                Integer resource = (Integer) img.getTag();
                resource = resource == null ? 0 : resource;
                Log.d("resource:", String.valueOf(resource));
                return resource == id;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("hasImage");
            }
        };
    }

    @Test
    public void cargaCamasPlanta() {
        Log.d("drawable", String.valueOf(R.drawable.cama_roja));
        onView(withParent(withId(R.id.rootPlanta))).check(matches(MisCamasTest.hasImage(R.drawable.cama_roja)));
    }
}