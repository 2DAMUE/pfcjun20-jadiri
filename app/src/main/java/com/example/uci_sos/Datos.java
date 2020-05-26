package com.example.uci_sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uci_sos.modelo.entidad.Hospital;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Crea un hospital nuevo, con un nombre y un ID único. Lleva a la ventana de configurar planta
 *
 * @see Hospital
 * @see ConfigPlanta
 */
public class Datos extends AppCompatActivity {

    /**
     * EditText que contiene el nombre del hospital
     */
    private EditText txtNombre;
    /**
     * EditText que contiene el número de plantas que posee un hospital
     */
    private EditText txtNumPlantas;

    /**
     * Botón que lleva a la ventana para configurar las plantas
     *
     * @see ConfigPlanta
     */
    private Button btnSiguiente;

    /**
     * Nombre del hospital
     */
    private String nombreHospital;

    /**
     * Número de plantas edl hospital
     */
    private int numPlantas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);
        setTitle("Datos");
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
        finish();
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
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comprobar())
                    crearHospital();
            }
        });
    }

    /**
     * Comprueba si se han rellenado todos los campos y si se han rellendao correctamente.
     *
     * @return tre si los datos son correctos. false en caso contrario
     */
    private boolean comprobar() {
        nombreHospital = txtNombre.getText().toString().trim();
        if (nombreHospital.equals("")) {
            showToast("Por favor, rellene todos los campos");
            return false;
        }
        try {
            numPlantas = Integer.parseInt(txtNumPlantas.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showToast("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de plantas");
            return false;
        }
        if (numPlantas <= 0) {
            showToast("Debe haber al menos una planta");
            return false;
        }
        return true;
    }

    /**
     * Crea un hospital con su nombre y abre la ventana de configurar plantas
     * pasándole el hospital y el número de plantas
     */
    private void crearHospital() {
        Hospital h = new Hospital();
        h.setNombre(nombreHospital);
        h.setCodHospital(0);
        Intent intent = new Intent(this, ConfigPlanta.class);
        intent.putExtra("hospital", h);
        intent.putExtra("numPlantas", numPlantas);
        finish();
        startActivity(intent);
    }

    /**
     * Muestra un toast dando un mensaje al usuario
     *
     * @param mensaje mensaje a mostrar
     */
    private void showToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    /**
     * Carga los elemento de la ventana
     */
    private void cargarVista() {
        txtNombre = findViewById(R.id.txtNombreHospital);
        txtNumPlantas = findViewById(R.id.txtNumPlantas);

        btnSiguiente = findViewById(R.id.btnToConfigPlantas);
    }
}
