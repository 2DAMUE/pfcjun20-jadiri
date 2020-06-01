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

/**
 * Ventana en la que se elige si se desea reservar una cama de su propio hospital o si se desea derivar a un paciente a otro hospital
 * registrado en la aplicación. Si se desea reservar una cama de su propio hospital, llevará a la ventana de Mis Camas.
 * Si se desea derivar a un paciente a otro hospital, llevará a la ventana de Buscar
 *
 * @see Buscar
 * @see MisCamas
 */
public class Reservar extends AppCompatActivity implements View.OnClickListener {

    /**
     * Botón para derivar a un paciente. Lleva a la ventana de Buscar
     *
     * @see Buscar
     */
    Button btnDerivar;
    /**
     * Botón para reservar una cama de su propio hospital. Lleva a la ventana de MisCamas
     *
     * @see MisCamas
     * @see Reservar#toMisCamasBuscar()
     */
    Button btnBuscar;

    /**
     * Botón que lleva a la ventana de Mi Hospital
     *
     * @see MiHospital
     */
    LinearLayout btnHosp;
    /**
     * Botón que lleva a la ventana de Mis Camas
     *
     * @see MisCamas
     * @see Reservar#toMisCamas()
     */
    LinearLayout btnCamas;

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
        btnCamas.setOnClickListener(this);
        btnDerivar.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);
        btnHosp.setOnClickListener(this);
    }

    /**
     * Carga los elemento de la ventana
     */
    private void cargarVista() {
        btnCamas = findViewById(R.id.btnMisCamasReserva);
        btnDerivar = findViewById(R.id.btnDerivar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnHosp = findViewById(R.id.btnHospitalReserva);
    }

    /**
     * Lleva a la ventana de Buscar
     *
     * @see Buscar
     */
    private void toBuscar() {
        Intent inte = new Intent(this.getApplicationContext(), Buscar.class);
        startActivity(inte);
    }

    /**
     * Lleva a la ventana de Mis Hospital
     *
     * @see MiHospital
     */
    private void toHospital() {
        Intent inte = new Intent(this.getApplicationContext(), MiHospitall.class);
        finish();
        startActivity(inte);
    }

    /**
     * Lleva a la ventana de Mis Camas eliminando el Stack de Activities
     *
     * @see MisCamas
     */
    private void toMisCamas() {
        Intent inte = new Intent(this.getApplicationContext(), MisCamas.class);
        finish();
        startActivity(inte);
    }

    /**
     * Lleva a la ventana de Mis Camas sin eliminar el Stack de activities ya que ha sido pulsado por el botón de buscar
     *
     * @see MisCamas
     */
    private void toMisCamasBuscar() {
        Intent inte = new Intent(this.getApplicationContext(), MisCamas.class);
        startActivity(inte);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnBuscar:
                toMisCamasBuscar();
                break;
            case R.id.btnMisCamasReserva:
                toMisCamas();
                break;
            case R.id.btnDerivar:
                toBuscar();
                break;
            case R.id.btnHospitalReserva:
                toHospital();
                break;
        }
    }
}

