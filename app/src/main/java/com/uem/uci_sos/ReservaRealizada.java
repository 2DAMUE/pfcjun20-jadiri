package com.uem.uci_sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Indica que la derivación ha sido realizada con éxito. Lleva a la ventana de Mi Hospital
 *
 * @see Reservar
 */
public class ReservaRealizada extends AppCompatActivity implements View.OnClickListener {

    /**
     * Botón que lleva a la ventana de Mi Hospital
     *
     * @see MiHospital
     */
    Button inicio;

    /**
     * Botón que lleva a la ventana de Mi Hospital
     *
     * @see MiHospital
     */
    LinearLayout mihospital;
    /**
     * Botón que lleva a la ventana de Mi Hospital
     *
     * @see MiHospital
     */
    LinearLayout miscamas;

    /**
     * TextView con el nombre del hospital al que se ha derivado al paciente
     */
    TextView nombreHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_realizada);
        setTitle("Reservar");
        String nombre = getIntent().getStringExtra("Nombre");
        nombreHospital = findViewById(R.id.lblHospitalReserva);
        nombreHospital.setText(nombre);
        cargarVista();
        cargarListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Carga los listeners de los elementos de la ventana
     */
    private void cargarListeners() {
        inicio.setOnClickListener(this);
        mihospital.setOnClickListener(this);
        miscamas.setOnClickListener(this);
    }

    /**
     * Carga los elementos de la vista
     */
    private void cargarVista() {
        inicio = findViewById(R.id.btninicio);
        mihospital = findViewById(R.id.btnHospitalReserre);
        miscamas = findViewById(R.id.btnMisCamasReserre);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnHospitalReserre:
            case R.id.btninicio:
                toHospital();
                break;
            case R.id.btnMisCamasReserre:
                toMisCamas();
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
     * Lleva a la ventana de Mi Hospital
     *
     * @see MiHospital
     */
    private void toHospital() {
        Intent inte = new Intent(this.getApplicationContext(), MiHospital.class);
        finish();
        startActivity(inte);
    }

    /**
     * Lleva a la ventana de Mis Camas
     *
     * @see MisCamas
     */
    private void toMisCamas() {
        Intent inte = new Intent(this.getApplicationContext(), MisCamas.class);
        finish();
        startActivity(inte);
    }
}