package com.example.uci_sos;


import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
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

    public static Matcher<View> childOf(final Matcher<View> parentMatcher, final int index) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                if (!(item.getParent() instanceof ViewGroup))
                    return parentMatcher.matches(item.getParent());
                ViewGroup parent = (ViewGroup) item.getParent();
                return parentMatcher.matches(parent) && parent.getChildAt(index).equals(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("childOf");
            }
        };
    }

    public static ViewAction swypeRefresh(final ViewAction action, final Matcher<View> constraints) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return constraints;
            }

            @Override
            public String getDescription() {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view) {
                action.perform(uiController, view);
            }
        };
    }

    @Test
    public void cargaCamasPlanta() {
        onView(childOf(childOf(withId(R.id.rootPlanta), 0), 0)).check(matches(hasImage(R.drawable.cama_roja)));
    }

    @Test
    public void cargaCamasUCI() {
        onView(childOf(childOf(withId(R.id.rootUCI), 0), 0)).check(matches(hasImage(R.drawable.cama_verde)));
    }

    @Test
    public void cargarCamasUrgencias() {
        onView(childOf(childOf(withId(R.id.rootUrgencias), 0), 0)).check(matches(hasImage(R.drawable.cama_amarilla)));
    }

    @Test
    public void cargaDialog() {
        onView(childOf(childOf(withId(R.id.rootPlanta), 0), 0)).perform(click());
        onView(withId(R.id.btnGuardarCama)).check(matches(isDisplayed()));
    }

    @Test
    public void cargarDatosCama() {
        onView(childOf(childOf(withId(R.id.rootPlanta), 0), 0)).perform(click());
        onView(withId(R.id.spEstado)).check(matches(withSpinnerText("Ocupada")));
        onView(withId(R.id.spPlanta)).check(matches(withSpinnerText("Planta de psiquiatría")));
        onView(withId(R.id.txtIdCama)).check(matches(withText("0")));
        onView(withId(R.id.chContagio)).check(matches(isNotChecked()));
    }

    @Test
    public void cancelar() {
        onView(childOf(childOf(withId(R.id.rootPlanta), 0), 0)).perform(click());
        onView(withId(R.id.btnCancelarCama)).perform(click());
        onView(withId(R.id.btnCancelarCama)).check(doesNotExist());
    }

    @Test
    public void guardarCama() {
        onView(childOf(childOf(withId(R.id.rootPlanta), 2), 0)).perform(click());
        onView(withId(R.id.txtIdCama)).perform(clearText(), typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.btnGuardarCama)).perform(click());
        onView(childOf(childOf(withId(R.id.rootPlanta), 2), 0)).perform(click());
        onView(withId(R.id.txtIdCama)).check(matches(withText("2")));
    }

    @Test
    public void refresh() {
        onView(withId(R.id.rootPlanta)).perform(swypeRefresh(swipeDown(), isDisplayingAtLeast(50)));
        onView(childOf(childOf(withId(R.id.rootPlanta), 0), 0)).check(matches(isDisplayed()));
    }

    @Test
    public void toHospital() {
        onView(withId(R.id.btnHospitalCamas)).perform(click());
        onView(withId(R.id.graficobarras)).check(matches(isDisplayed()));
    }

    @Test
    public void toReservar() {
        onView(withId(R.id.btnReservarCamas)).perform(click());
        onView(withId(R.id.btnBuscar)).check(matches(isDisplayed()));
    }
}