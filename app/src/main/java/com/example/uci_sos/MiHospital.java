package com.example.uci_sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.uci_sos.modelo.entidad.Hospital;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Muestra los datos del Hospital en el que está registrado el usuario
 *
 * @see Hospital
 */
public class MiHospital extends AppCompatActivity implements View.OnClickListener {

    /**
     * Botón que lleva a la ventana de Reservar
     *
     * @see Reservar
     */
    LinearLayout reservar;
    /**
     * Botón que lleva a la ventana de MisCamas
     *
     * @see MisCamas
     */
    LinearLayout miscamas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityhospital);
        cargarVista();
        cargarListeners();
        setupPieChart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnReservarHospital:
                toReservar();
                break;
            case R.id.btnMisCamasHospital:
                toCamas();
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itLogout:
                FirebaseAuth.getInstance().signOut();
                toLogin();
                break;
            case R.id.itConfig:
                toConfig();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Carga los datos de los hospitales en el gráfico
     *
     * @see Hospital
     */
    private void setupPieChart() {
        String[] estados = {"Libres", "Ocupadas", "No disponibles"};
        int[] numeros = {250, 102, 50};
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < estados.length; i++) {
            pieEntries.add(new PieEntry(numeros[i], estados[i]));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "Estado??");
        PieData data = new PieData(dataSet);
        dataSet.setColors(getResources().getColor(R.color.colorLibres), getResources().getColor(R.color.colorOcupadas), getResources().getColor(R.color.colorNoDisponible));

        PieChart chart = findViewById(R.id.grafico_circulo);
        chart.getLegend().setEnabled(false);
        chart.setData(data);
        chart.invalidate();
        chart.animateXY(1000, 1000);

    }

    /**
     * Lleva a la ventana de configuración del hospital
     *
     * @see Datos
     */
    private void toConfig() {
        Intent inte = new Intent(this, Datos.class);
        startActivity(inte);
    }

    /**
     * Lleva al login
     *
     * @see Login
     */
    private void toLogin() {
        Intent inte = new Intent(this, Login.class);
        inte.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(inte);
    }

    /**
     * Lleva a la ventana de Reservar
     *
     * @see Reservar
     */
    private void toReservar() {
        Intent inte = new Intent(this.getApplicationContext(), Reservar.class);
        finish();
        startActivity(inte);
    }

    /**
     * Lleva a la ventana de MisCamas
     *
     * @see MisCamas
     */
    private void toCamas() {
        Intent inte = new Intent(this.getApplicationContext(), MisCamas.class);
        finish();
        startActivity(inte);
    }

    /**
     * Carga los listeners de los elementos de la ventana
     */
    private void cargarListeners() {
        reservar.setOnClickListener(this);
        miscamas.setOnClickListener(this);
    }

    /**
     * Carga los elementos de la ventana
     */
    private void cargarVista() {
        reservar = findViewById(R.id.btnReservarHospital);
        miscamas = findViewById(R.id.btnMisCamasHospital);
    }
}