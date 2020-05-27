package com.example.uci_sos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReservaRealizada extends AppCompatActivity implements View.OnClickListener{
    Button inicio;
    LinearLayout mihospital;
    LinearLayout miscamas;
    TextView nombreh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_realizada);
        setTitle("Reservar");
        String nombre = getIntent().getStringExtra("Nombre");
        nombreh = findViewById(R.id.lblHospitalReserva);
        nombreh.setText(nombre);
        cargarVista();
        cargarListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    private void cargarListeners() {
        inicio.setOnClickListener(this);
        mihospital.setOnClickListener(this);
        miscamas.setOnClickListener(this);
    }


    private void cargarVista() {
        inicio = findViewById(R.id.btninicio);
        mihospital = findViewById(R.id.btnHospitalReserre);
        miscamas = findViewById(R.id.btnMisCamasReserre);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btninicio:
                toHospital();
                break;
            case R.id.btnHospitalReserre:
                toHospital();
                break;
            case R.id.btnMisCamasReserre:
                tomisCamas();
                break;
        }
    }





    private void toHospital() {
        Intent inte = new Intent(this.getApplicationContext(), MiHospital.class);
        finish();
        startActivity(inte);
    }
    private void tomisCamas() {
        Intent inte = new Intent(this.getApplicationContext(), MisCamas.class);
        finish();
        startActivity(inte);
    }

}