package com.example.uci_sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uci_sos.modelo.entidad.Hospital;

import com.example.uci_sos.modelo.entidad.Camas;
import com.example.uci_sos.modelo.entidad.Planta;
import com.example.uci_sos.modelo.entidad.Referencias;
import com.example.uci_sos.modelo.entidad.Urgencias;
import com.example.uci_sos.modelo.entidad.UCI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Añade las camas de un hospital planta a planta.
 *
 * @see Datos
 * @see Hospital
 * @see Camas
 * @see Planta
 * @see Urgencias
 * @see UCI
 */
public class ConfigPlanta extends AppCompatActivity {

    private FirebaseDatabase db;
    /**
     * Referencia al child de hospitales
     */
    private DatabaseReference hospitales;

    /**
     * EditText que contiene el nombre de la planta actual del hospital
     */
    private EditText txtNombrePlanta;

    /**
     * EditText que contiene el número de camas de UCI libres
     */
    private EditText txtNumCamasUCILibres;
    /**
     * EditText que contiene el número de camas de UCI ocupadas
     */
    private EditText txtNumCamasUCIOcupadas;

    /**
     * EditText que contiene el número de camas de Planta libres
     */
    private EditText txtNumCamasPlantaLibres;
    /**
     * EditText que contiene el número de camas de Planta ocupadas
     */
    private EditText txtNumCamasPlantaOcupadas;

    /**
     * EditText que contiene el número de camas de Urgencias libres
     */
    private EditText txtNumCamasUrgenciasLibres;
    /**
     * EditText que contiene el número de camas de Urgencias ocupadas
     */
    private EditText txtNumCamasUrgenciasOcupadas;


    /**
     * Botón que actualiza la interfaz para ir editando las diferentes plantas.
     * Cuando se termina de editar las plantas guarda el hospital en la BBDD
     */
    private Button btnSiguiente;

    /**
     * Hospital creado en la ventana anterior.
     *
     * @see Hospital
     */
    private Hospital h;

    /**
     * Indica que número de planta se está editando
     */
    private int index;
    /**
     * Número de plantas con las que se configuró el hospital
     */
    private int numPlantas;
    /**
     * Número de camas de UCI libres en la planta actual
     */
    private int numCamasUCILibres;
    /**
     * Número de camas de UCI ocupadas en la planta actual
     */
    private int numCamasUCIOcupadas;
    /**
     * Número de camas de Planta libres en la planta actual
     */
    private int numCamasPlantaLibres;
    /**
     * Número de camas de Planta ocupadas en la planta actual
     */
    private int numCamasPlantaOcupadas;
    /**
     * Número de camas de Urgencias libres en la planta actual
     */
    private int numCamasUrgenciasLibres;
    /**
     * Número de camas de Urgencias ocupadas en la planta actual
     */
    private int numCamasUrgenciasOcupadas;

    /**
     * Nombre de la planta actual del hospital
     */
    private String nombrePLanta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_planta);
        h = (Hospital) getIntent().getSerializableExtra("hospital");
        index = 0;
        numPlantas = getIntent().getIntExtra("numPlantas", 1);
        db = FirebaseDatabase.getInstance();
        hospitales = db.getReference(Referencias.HOSPITALES);
        cargarVista();
        cargarListeners();
        actualizarVista();
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
     * Muestra un toast dando un mensaje al usuario
     *
     * @param mensaje mensaje a mostrar
     */
    private void showToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    /**
     * Carga los listeners de los elementos de la ventana
     */
    private void cargarListeners() {
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comprobar())
                    actualizarVista();
            }
        });
    }

    /**
     * Actualiza la vista mostrando que planta se está configurando, vaciando los campos en cada iteración,
     * actializando el título de la ventana y creando las camas indicadas. Y, cuando sea la última planta, se guarda el hospital
     *
     * @see ConfigPlanta#crearCamas()
     * @see ConfigPlanta#clear()
     */
    private void actualizarVista() {
        index++;
        if (btnSiguiente.getText().equals("Crear Hospital")) {
            crearCamas();
            guardarHospital();
        } else {
            crearCamas();
            clear();
            setTitle(index + "/" + numPlantas);
            if (index == numPlantas)
                btnSiguiente.setText("Crear Hospital");
        }
    }

    /**
     * Limpia los campos para prepararlos para la siguiente configuración de planta
     */
    private void clear() {
        txtNombrePlanta.setText("");

        txtNumCamasUCILibres.setText("");
        txtNumCamasUCIOcupadas.setText("");

        txtNumCamasPlantaLibres.setText("");
        txtNumCamasPlantaOcupadas.setText("");

        txtNumCamasUrgenciasLibres.setText("");
        txtNumCamasUrgenciasOcupadas.setText("");
    }

    /**
     * Añade las camas a la planta actual del hospital
     *
     * @see Hospital
     * @see Camas
     * @see UCI
     * @see Planta
     * @see Urgencias
     */
    private void crearCamas() {
        for (int i = 0; i < numCamasUCILibres; i++) {
            h.addCama(new UCI("libre", 0, nombrePLanta, false));
        }
        for (int i = 0; i < numCamasUCIOcupadas; i++) {
            h.addCama(new UCI("ocupado", 0, nombrePLanta, false));
        }

        for (int i = 0; i < numCamasPlantaLibres; i++) {
            h.addCama(new Planta("libre", 0, nombrePLanta, false));
        }
        for (int i = 0; i < numCamasPlantaOcupadas; i++) {
            h.addCama(new Planta("ocupado", 0, nombrePLanta, false));
        }

        for (int i = 0; i < numCamasUrgenciasLibres; i++) {
            h.addCama(new Urgencias("libre", 0, nombrePLanta, false));
        }
        for (int i = 0; i < numCamasUrgenciasOcupadas; i++) {
            h.addCama(new Urgencias("ocupado", 0, nombrePLanta, false));
        }
    }

    /**
     * Comprueba si se han rellenado todos los campos y si se han rellenado correctamente
     *
     * @return true si los datos son correctos. false en caso contrario
     */
    private boolean comprobar() {
        nombrePLanta = txtNombrePlanta.getText().toString().trim();

        try {
            numCamasUCILibres = Integer.parseInt(txtNumCamasUCILibres.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showToast("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de UCI libres");
            return false;
        }
        try {
            numCamasUCIOcupadas = Integer.parseInt(txtNumCamasUCIOcupadas.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showToast("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de UCI ocupadas");
            return false;
        }
        try {
            numCamasPlantaLibres = Integer.parseInt(txtNumCamasPlantaLibres.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showToast("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de planta libres");
            return false;
        }
        try {
            numCamasPlantaOcupadas = Integer.parseInt(txtNumCamasPlantaOcupadas.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showToast("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de planta ocupadas");
            return false;
        }
        try {
            numCamasUrgenciasLibres = Integer.parseInt(txtNumCamasUrgenciasLibres.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showToast("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de urgencias libres");
            return false;
        }
        try {
            numCamasUrgenciasOcupadas = Integer.parseInt(txtNumCamasUrgenciasOcupadas.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showToast("Por favor, rellene todos los campos\nO introduzca un número entero indicando el número de camas de urgencias ocupadas");
            return false;
        }
        if (nombrePLanta.equals("")) {
            showToast("Por favor rellene todos los campos");
            return false;
        }
        return true;
    }

    private void guardarHospital() {
        hospitales.child(String.valueOf(h.getCodHospital())).setValue(h)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("CREAR_HOSPITAL", "ÉXITO");
                            showToast("Hospital creado con éxito");
                        } else {
                            Log.w("CREAR_USER", task.getException().toString());
                            showToast("Ha habido un error al crear el hospital\nCompruebe su conexión a Internet e inténtelo de nuevo más tarde");
                            toDatos();
                        }
                    }
                });
    }

    /**
     * Lleva a la ventana de creación del hospital.
     *
     * @see Datos
     */
    private void toDatos() {
        Intent intent = new Intent(this, Datos.class);
        finish();
        startActivity(intent);
    }

    /**
     * Carga los elemento de la ventana
     */
    private void cargarVista() {
        txtNombrePlanta = findViewById(R.id.txtNombrePlanta);

        txtNumCamasUCILibres = findViewById(R.id.txtNumCamasUCILibres);
        txtNumCamasUCIOcupadas = findViewById(R.id.txtNumCamasUCIOcupadas);

        txtNumCamasPlantaLibres = findViewById(R.id.txtNumCamasPlantaLibres);
        txtNumCamasPlantaOcupadas = findViewById(R.id.txtNumCamasPlantaOcupadas);

        txtNumCamasUrgenciasLibres = findViewById(R.id.txtNumCamasUrgenciasLibres);
        txtNumCamasUrgenciasOcupadas = findViewById(R.id.txtNumCamasUrgenciasOcupadas);

        btnSiguiente = findViewById(R.id.btnSiguiente);
    }
}
