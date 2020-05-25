package com.example.uci_sos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Reservar extends Activity implements View.OnClickListener {
    Button derivar;
    Button buscar;
    LinearLayout mihospital;
    LinearLayout miscamas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);
        setTitle("Reservar");
        cargarVista();
        cargarListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void cargarListeners() {
        derivar.setOnClickListener(this);
        buscar.setOnClickListener(this);
        mihospital.setOnClickListener(this);
        miscamas.setOnClickListener(this);
    }


    private void cargarVista() {
        derivar = findViewById(R.id.btnDerivar);
        buscar = findViewById(R.id.btnBuscar);
        mihospital = findViewById(R.id.btnHospitalReserva);
        miscamas = findViewById(R.id.btnMisCamasReserva);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnDerivar:
                toBuscar();
                break;
            case R.id.btnBuscar:
                toCamas();
                break;
            case R.id.btnHospitalReserva:
                toHospital();
                break;
            case R.id.btnMisCamasReserva:
                tomisCamas();
                break;
        }
    }


    private void toBuscar() {
        Intent inte = new Intent(this.getApplicationContext(), Buscar.class);
        startActivity(inte);
    }

    private void toCamas() {
        Intent inte = new Intent(this.getApplicationContext(), Login.class);
        finish();
        startActivity(inte);
    }
    private void toHospital() {
        Intent inte = new Intent(this.getApplicationContext(), Login.class);
        finish();
        startActivity(inte);
    }
    private void tomisCamas() {
        Intent inte = new Intent(this.getApplicationContext(), Login.class);
        finish();
        startActivity(inte);
    }

}


