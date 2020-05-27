package com.example.uci_sos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.example.uci_sos.modelo.entidad.Hospital;

public class MisCamas extends AppCompatActivity implements View.OnClickListener{
    LinearLayout reservar;
    LinearLayout miscamas;
    LinearLayout mihospital;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_camas);
        cargarVista();
        cargarListeners();
    }
    private void cargarListeners() {
        reservar.setOnClickListener(this);

        mihospital.setOnClickListener(this);

    }


    private void cargarVista() {
        reservar = findViewById(R.id.btnReservarCamas);
        miscamas = findViewById(R.id.btnMisCamasHospital);
        mihospital = findViewById(R.id.btnHospitalCamas);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnReservarCamas:
                toReservar();
                break;
            case R.id.btnHospitalCamas:
                toHospital();
                break;

        }
    }


    private void toReservar() {
        Intent inte = new Intent(this.getApplicationContext(), Reservar.class);
        startActivity(inte);
    }



    private void toHospital() {
        Intent inte = new Intent(this.getApplicationContext(), MiHospital.class);
        finish();
        startActivity(inte);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}