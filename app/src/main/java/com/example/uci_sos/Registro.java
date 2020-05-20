package com.example.uci_sos;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Registro extends Activity {

    private Spinner spinHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        cargarVista();
        cargarListeners();
    }

    private void cargarListeners() {
    }

    private void cargarVista() {
        spinHospital = findViewById(R.id.spinnerRegistro);
        cargarAdapter();
    }

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
}
