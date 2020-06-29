package com.uem.uci_sos;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Métodos personalizados para los test con Espresso
 *
 * @author Ricardo Bordería Pi
 */
public class MetodosPersonalizados {

    /**
     * Método para probar la funcionalidad del SwipeRefreshLayout
     *
     * @param action      acción a realizar (swipeDown() en este caso)
     * @param constraints distancia a deslizar
     * @return nuevo ViewAction con la acción de deslizar hacia abajo
     */
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

    /**
     * Devuelve el hijo del padre en el ínice indicado
     *
     * @param parentMatcher elemento padre
     * @param index         índice del elemento a seleccionar
     * @return elemento hijo en la posición indicada
     */
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
                try {
                    ViewGroup parent = (ViewGroup) item.getParent();
                    return parentMatcher.matches(parent) && parent.getChildAt(index).equals(item);
                } catch (NullPointerException e) {
                    return parentMatcher.matches(item);
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("childOf");
            }
        };
    }
}
