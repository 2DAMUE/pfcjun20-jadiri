package com.example.uci_sos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Datos extends AppCompatActivity implements View.OnClickListener {
    LinearLayout reservar;
    LinearLayout miscamas;
    LinearLayout mihospital;
    Button siguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);
        setTitle("Datos");
        cargarVista();
        cargarListeners();

    }

    private void cargarListeners() {
        reservar.setOnClickListener(this);
        miscamas.setOnClickListener(this);
        mihospital.setOnClickListener(this);
        siguiente.setOnClickListener(this);
    }


    private void cargarVista() {
        reservar = findViewById(R.id.btnReservarDatos);
        miscamas = findViewById(R.id.btnMisCamasDatos);
        siguiente = findViewById(R.id.btnToConfigPlantas);
        mihospital = findViewById(R.id.btnHospitalDatos);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnReservarDatos:
                toReservar();
                break;
            case R.id.btnMisCamasDatos:
                toCamas();
                break;
            case R.id.btnToConfigPlantas:
                toPlanta();
                break;
            case R.id.btnHospitalDatos:
                toPlanta();
                break;
        }
    }


    private void toReservar() {
        Intent inte = new Intent(this.getApplicationContext(), Reservar.class);
        startActivity(inte);
    }

    private void toCamas() {
        Intent inte = new Intent(this.getApplicationContext(), Login.class);
        finish();
        startActivity(inte);
    }

    private void toPlanta() {
        Intent inte = new Intent(this.getApplicationContext(), ConfigPlanta.class);
        finish();
        startActivity(inte);
    }

    private void toHospital() {
        Intent inte = new Intent(this.getApplicationContext(), Login.class);
        finish();
        startActivity(inte);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
