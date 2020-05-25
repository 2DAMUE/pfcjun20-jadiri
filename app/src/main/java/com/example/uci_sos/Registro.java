package com.example.uci_sos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Registro de la aplicación. En él creas tu usuario para l aaplicación
 */
public class Registro extends Activity implements View.OnClickListener{

    /**
     * Spinner con la lista de hospitales registrados en la aplicación
     */
    private Spinner spinHospital;
    Button regis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        cargarVista();
        cargarListeners();

    }


    /**
     * Carga los listeners de los elementos de la ventana
     */
    private void cargarListeners() {
        regis.setOnClickListener(this);

    }

    /**
     * Carga los elemento de la ventana
     */
    private void cargarVista() {
        regis = findViewById(R.id.btnRegistro);
        spinHospital = findViewById(R.id.spinnerRegistro);
        cargarAdapter();
    }

    /**
     * Crea y carga la lista de hospitales en el spinner. Inhabilita el elemento de "Seleccionar hospital", el primero del spinner.
     *
     * @see Registro#spinHospital
     */
    private void cargarAdapter() {
        String[] nombres = new String[]{"Selecciona un hospital", "Puerta de Hierro", "Gregorio Marañón", "Montepríncipe"};
        List<String> listaNombres = new ArrayList<>(Arrays.asList(nombres));
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.spinner_registro_item, listaNombres) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                if (position == 0) {
                    TextView item = (TextView) v;
                    item.setTextColor(Color.GRAY);
                }
                return v;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinHospital.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnRegistro:
                toLogin();
                break;
        }
    }



    private void toLogin() {
        Intent inte = new Intent(this.getApplicationContext(), Login.class);
        startActivity(inte);
    }



}
