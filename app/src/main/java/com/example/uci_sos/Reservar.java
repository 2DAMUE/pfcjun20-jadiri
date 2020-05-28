package com.example.uci_sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class Reservar extends AppCompatActivity implements View.OnClickListener {
    LinearLayout btnhosp;
    Button derivar;
    Button buscar;
    LinearLayout btncamas;

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
     * Carga los listeners de los elementos de la ventana
     */
    private void cargarListeners() {
        btncamas.setOnClickListener(this);
        derivar.setOnClickListener(this);
        buscar.setOnClickListener(this);
        btnhosp.setOnClickListener(this);
    }

    /**
     * Carga los elemento de la ventana
     */
    private void cargarVista() {
        btncamas = findViewById(R.id.btnMisCamasReserva);
        derivar = findViewById(R.id.btnDerivar);
        buscar = findViewById(R.id.btnBuscar);
        btnhosp = findViewById(R.id.btnHospitalReserva);


    }

    /**
     * Lleva al registro
     *
     * @see Registro
     */
    private void toBuscar() {
        Intent inte = new Intent(this.getApplicationContext(), Buscar.class);
        startActivity(inte);
    }

    private void toDerivar() {
        Intent inte = new Intent(this.getApplicationContext(), Splash.class);
        finish();
        startActivity(inte);
    }

    private void toHospital() {
        Intent inte = new Intent(this.getApplicationContext(), MiHospital.class);
        finish();
        startActivity(inte);
    }

    private void toMisCamas() {
        Intent inte = new Intent(this.getApplicationContext(), MisCamas.class);
        finish();
        startActivity(inte);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnBuscar:
                toMisCamas();
                break;
            case R.id.btnDerivar:
                toBuscar();
                break;
            case R.id.btnHospitalReserva:
                toHospital();
                break;
            case R.id.btnMisCamasReserva:
                toMisCamas();
                break;
        }
    }
}

