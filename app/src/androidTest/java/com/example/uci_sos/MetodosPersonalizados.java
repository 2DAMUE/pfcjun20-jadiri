package com.example.uci_sos;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class MetodosPersonalizados {

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
                if (item instanceof RecyclerView) {
                    RecyclerView recycler = (RecyclerView) item;
                    return parentMatcher.matches(recycler.getChildAt(index));
                }
                ViewGroup parent = (ViewGroup) item.getParent();
                return parentMatcher.matches(parent) && parent.getChildAt(index).equals(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("childOf");
            }
        };
    }


}
