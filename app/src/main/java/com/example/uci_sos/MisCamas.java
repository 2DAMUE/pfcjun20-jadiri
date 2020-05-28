package com.example.uci_sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.uci_sos.modelo.entidad.Hospital;
import com.google.firebase.auth.FirebaseAuth;

public class MisCamas extends AppCompatActivity implements View.OnClickListener {

    /**
     * Botón que lleva a la ventana de Reservar
     *
     * @see Reservar
     */
    LinearLayout reservar;
    /**
     * Botón que lleva a la ventana de Mi Hospital
     *
     * @see MiHospital
     */
    LinearLayout mihospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_camas);
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

    /**
     * Carga los listeners de los elementos de la ventana
     */
    private void cargarListeners() {
        reservar.setOnClickListener(this);
        mihospital.setOnClickListener(this);
    }

    /**
     * Carga los elementos de la vista
     */
    private void cargarVista() {
        reservar = findViewById(R.id.btnReservarCamas);
        mihospital = findViewById(R.id.btnHospitalCamas);
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
     * Lleva a la ventana de Mi Hospital
     */
    private void toHospital() {
        Intent inte = new Intent(this.getApplicationContext(), MiHospital.class);
        finish();
        startActivity(inte);
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
}